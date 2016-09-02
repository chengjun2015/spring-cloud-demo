package com.gavin.service.impl;

import com.gavin.client.payment.PaymentClient;
import com.gavin.client.point.PointClient;
import com.gavin.client.product.ProductClient;
import com.gavin.constant.CacheNameConsts;
import com.gavin.constant.ResponseCodeConsts;
import com.gavin.dao.ItemDao;
import com.gavin.dao.OrderDao;
import com.gavin.domain.order.Item;
import com.gavin.domain.order.Order;
import com.gavin.domain.payment.Payment;
import com.gavin.enums.OrderStatusEnums;
import com.gavin.exception.order.OrderException;
import com.gavin.model.RestResult;
import com.gavin.model.request.payment.CreatePaymentReqModel;
import com.gavin.model.request.point.FreezePointReqModel;
import com.gavin.model.request.product.ReserveProductsReqModel;
import com.gavin.model.response.Response;
import com.gavin.model.response.order.OrderDetailModel;
import com.gavin.model.response.product.ProductDetailModel;
import com.gavin.model.response.product.ReserveProductsResModel;
import com.gavin.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OrderDao orderDao;

    @Resource
    private ItemDao itemDao;

    @Resource
    private ProductClient productClient;

    @Resource
    private PointClient pointClient;

    @Resource
    private PaymentClient paymentClient;

    @Override
    @Transactional
    public void createOrder(Order order, Item[] items) {
        order.setStatus(OrderStatusEnums.ORDER_STATUS_CREATED.getValue());
        orderDao.create(order);

        Long orderId = order.getId();

        for (Item item : items) {
            item.setOrderId(orderId);
            itemDao.create(item);
        }
    }

    @Override
    @Cacheable(cacheNames = CacheNameConsts.CACHE_ORDERS_BY_ID, key = "#orderId")
    public OrderDetailModel searchOrderByOrderId(Long orderId) {
        return orderDao.searchById(orderId);
    }

    @Override
    @Cacheable(cacheNames = CacheNameConsts.CACHE_ORDERS_BY_ACCOUNTID, key = "#accountId")
    public List<OrderDetailModel> searchOrdersByAccountId(Long accountId) {
        return orderDao.searchByAccountId(accountId);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheNameConsts.CACHE_ORDERS_BY_ID, key = "#order.id")
    public void updateOrder(Order order) {
        orderDao.update(order);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheNameConsts.CACHE_ORDERS_BY_ID, key = "#order.id")
    public void updateStatus(Order order) {
        orderDao.updateStatus(order);
    }

    @Override
    @HystrixCommand(fallbackMethod = "reserveProductsFallback", ignoreExceptions = {OrderException.class})
    public RestResult<List<ProductDetailModel>> reserveProducts(Item[] items) {
        RestResult<List<ProductDetailModel>> result = new RestResult<>(false);

        ReserveProductsReqModel reserveReqModel = new ReserveProductsReqModel();
        reserveReqModel.setItems(items);
        // 调用product服务尝试锁定库存。
        Response<ReserveProductsResModel> response = productClient.reserveProducts(reserveReqModel);
        if (ResponseCodeConsts.CODE_PRODUCT_NORMAL.equals(response.getCode())) {
            logger.info("调用product微服务保留订单中商品个数成功。");
            ReserveProductsResModel reserveResModel = response.getData();
            result.setData(reserveResModel.getProductDetails());
        } else if (ResponseCodeConsts.CODE_PRODUCT_INSUFFICIENT_STOCK.equals(response.getCode())) {
            logger.warn("订购商品的库存不足。");
        } else {
            logger.error("product微服务保留订单中商品个数失败。");
        }
        return result;
    }

    private RestResult<List<ProductDetailModel>> reserveProductsFallback(Item[] items) {
        logger.warn("调用reserveProducts方法时触发断路器。");
        return new RestResult<>(true);
    }

    @Override
    @HystrixCommand(fallbackMethod = "reservePointsFallback")
    public RestResult<Boolean> reservePoints(Long accountId, Long orderId, BigDecimal amount) {
        RestResult<Boolean> result = new RestResult<>(false);

        FreezePointReqModel reserveReqModel = new FreezePointReqModel();
        reserveReqModel.setOrderId(orderId);
        reserveReqModel.setAmount(amount);

        Response response = pointClient.reservePoints(reserveReqModel);
        if (ResponseCodeConsts.CODE_POINT_NORMAL.equals(response.getCode())) {
            logger.info("调用point微服务冻结积分" + amount + "点成功。");
            result.setData(true);
        } else if (ResponseCodeConsts.CODE_POINT_INSUFFICIENT_BALANCE.equals(response.getCode())) {
            logger.info("积分余额不足。");
            logger.error(response.getMessage());
            result.setData(false);
        } else {
            result.setData(false);
        }
        return result;
    }

    private RestResult<Boolean> reservePointsFallback(Long accountId, Long orderId, BigDecimal amount) {
        logger.warn("调用reservePoints方法时触发断路器。");
        return new RestResult<>(true);
    }

    @Override
    @HystrixCommand(fallbackMethod = "createPaymentFallback")
    public RestResult<Payment> createPayment(Long accountId, Long orderId, BigDecimal amount, Integer paymentMethod) {
        RestResult<Payment> result = new RestResult<>(false);

        CreatePaymentReqModel createReqModel = new CreatePaymentReqModel();
        createReqModel.setAccountId(accountId);
        createReqModel.setOrderId(orderId);
        createReqModel.setAmount(amount);
        createReqModel.setPaymentMethod(paymentMethod);

        Response<Payment> response = paymentClient.createPayment(createReqModel);
        if (ResponseCodeConsts.CODE_PAYMENT_NORMAL.equals(response.getCode())) {
            logger.info("调用payment微服务创建账单成功。");
            result.setData(response.getData());
        }

        return result;
    }

    private RestResult<Payment> createPaymentFallback(Long accountId, Long orderId, BigDecimal amount, Integer paymentMethod) {
        logger.warn("调用createPayment方法时触发断路器。");
        return new RestResult<>(true);
    }

}
