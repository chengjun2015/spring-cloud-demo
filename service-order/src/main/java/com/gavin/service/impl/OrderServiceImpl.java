package com.gavin.service.impl;

import com.gavin.client.point.PointClient;
import com.gavin.client.payment.PaymentClient;
import com.gavin.client.product.ProductClient;
import com.gavin.constant.CacheNameConsts;
import com.gavin.constant.ResponseCodeConsts;
import com.gavin.dao.ItemDao;
import com.gavin.dao.OrderDao;
import com.gavin.domain.order.Item;
import com.gavin.domain.order.Order;
import com.gavin.enums.OrderStatusEnums;
import com.gavin.exception.order.OrderException;
import com.gavin.model.order.OrderDetailModel;
import com.gavin.model.order.OrderModel;
import com.gavin.request.point.ReservePointReq;
import com.gavin.response.Response;
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
    private ProductClient productClient;

    @Resource
    private PointClient pointClient;

    @Resource
    private PaymentClient paymentClient;

    @Resource
    private OrderDao orderDao;

    @Resource
    private ItemDao itemDao;

    @Override
    @HystrixCommand(fallbackMethod = "reserveProductsFallback", ignoreExceptions = {OrderException.class})
    public OrderDetailModel reserveProducts(Item[] items) {
        // 调用product服务查询库存,锁定库存,计算订单总金额和可获得的积分数。
        OrderDetailModel orderDetail = productClient.reserve(items);
        return orderDetail;
    }

    private OrderDetailModel reserveProductsFallback(Item[] items) {
        logger.warn("调用reserveProducts方法时触发断路器。");
        return null;
    }

    @Override
    @Transactional
    public void createOrder(OrderModel orderModel) {
        Order order = orderModel.getOrder();
        order.setStatus(OrderStatusEnums.ORDER_STATUS_CREATED.getValue());
        orderDao.create(order);

        Long orderId = order.getId();

        for (Item item : orderModel.getItems()) {
            item.setOrderId(orderId);
            itemDao.create(item);
        }
    }

    @Override
    @HystrixCommand(fallbackMethod = "reservePointsFallback")
    public Boolean reservePoints(Long accountId, Long orderId, BigDecimal amount) {
        ReservePointReq reservePointReq = new ReservePointReq();
        reservePointReq.setOrderId(orderId);
        reservePointReq.setAmount(amount);
        Response response = pointClient.reservePoints(accountId, reservePointReq);
        if (ResponseCodeConsts.CODE_POINT_NORMAL.equals(response.getCode())) {
            logger.info("调用point微服务成功锁定" + amount + "积分。");
            return true;
        } else {
            logger.error(response.getMessage());
            return false;
        }
    }

    private Boolean reservePointsFallback(Long accountId, Long orderId, BigDecimal amount) {
        logger.warn("调用reservePoints方法时触发断路器。");
        return null;
    }

    @Override
    @HystrixCommand(fallbackMethod = "createPaymentFallback")
    public Long createPayment(Long accountId, Long orderId, BigDecimal amount, Integer paymentMethod) {
        return paymentClient.createPayment(accountId, orderId, amount, paymentMethod);
    }

    private Long createPaymentFallback(Long accountId, Long orderId, BigDecimal amount, Integer paymentMethod) {
        logger.warn("调用createPayment方法时触发断路器。");
        return null;
    }

    @Override
    @Cacheable(cacheNames = CacheNameConsts.CACHE_ORDERS_BY_ID, key = "#orderId")
    public OrderModel searchOrderByOrderId(Long orderId) {
        return orderDao.searchById(orderId);
    }

    @Override
    @Cacheable(cacheNames = CacheNameConsts.CACHE_ORDERS_BY_ACCOUNTID, key = "#accountId")
    public List<OrderModel> searchOrdersByAccountId(Long accountId) {
        return orderDao.searchByAccountId(accountId);
    }

    @Override
    @CacheEvict(cacheNames = CacheNameConsts.CACHE_ORDERS_BY_ID, key = "#order.id")
    @Transactional
    public void updateOrder(Order order) {
        orderDao.update(order);
    }

    @Override
    @CacheEvict(cacheNames = CacheNameConsts.CACHE_ORDERS_BY_ID, key = "#order.id")
    @Transactional
    public void updateStatus(Order order) {
        orderDao.updateStatus(order);
    }
}
