package com.gavin.controller;

import com.gavin.constant.ResponseCodeConsts;
import com.gavin.domain.order.Item;
import com.gavin.domain.order.Order;
import com.gavin.domain.payment.Payment;
import com.gavin.enums.OrderStatusEnums;
import com.gavin.model.request.order.CreateOrderReqModel;
import com.gavin.model.request.order.PayOrderReqModel;
import com.gavin.model.response.Response;
import com.gavin.model.response.order.SearchOrderResModel;
import com.gavin.model.response.product.ProductDetailModel;
import com.gavin.model.response.order.OrderDetailModel;
import com.gavin.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/{account_id}/orders", method = RequestMethod.POST)
    public Response<OrderDetailModel> createOrder(@PathVariable("account_id") Long accountId,
                                                  @Valid @RequestBody CreateOrderReqModel model) {
        Response<OrderDetailModel> response;

        Item[] items = model.getItems();
        List<ProductDetailModel> productDetails = orderService.reserveProducts(items);

        if (null == productDetails) {
            logger.warn("调用product服务失败。");
            response = new Response(ResponseCodeConsts.CODE_ORDER_RESERVE_FAILED);
            response.setMessage("调用product服务失败。");
            return response;
        }

        if (productDetails.size() == 0) {
            logger.warn("product服务无法确保订单中的某些商品。");
            response = new Response(ResponseCodeConsts.CODE_ORDER_RESERVE_FAILED);
            response.setMessage("product服务无法确保订单中的某些商品。");
            return response;
        }

        logger.info("订购的商品全部预约成功。");

        BigDecimal totalPricePerOrder = new BigDecimal("0");
        BigDecimal totalPointsPerOrder = new BigDecimal("0");

        for (ProductDetailModel productDetail : productDetails) {
            // 计算该种商品的总金额。
            BigDecimal totalPricePerItem = new BigDecimal(productDetail.getPrice() * productDetail.getQuantity());

            // 计算该种商品可获得的积分数。
            BigDecimal totalPointsPerItem = new BigDecimal("0");
            if (null != productDetail.getRatio()) {
                totalPointsPerItem = totalPricePerItem.multiply(new BigDecimal(productDetail.getRatio())).setScale(0, BigDecimal.ROUND_HALF_UP);
            }

            totalPricePerOrder = totalPricePerOrder.add(totalPricePerItem);
            totalPointsPerOrder = totalPricePerOrder.add(totalPointsPerItem);
        }

        Order order = new Order();
        order.setAccountId(accountId);
        order.setAddressId(model.getAddressId());
        order.setTotalPrice(totalPricePerOrder);
        order.setRewardPoints(totalPointsPerOrder);

        orderService.createOrder(order, items);

        logger.info("订单" + order.getId() + "创建成功。");
        OrderDetailModel orderDetail = orderService.searchOrderByOrderId(order.getId());
        response = new Response(ResponseCodeConsts.CODE_ORDER_NORMAL);
        response.setData(orderDetail);
        return response;
    }

    @RequestMapping(value = "/{account_id}/orders", method = RequestMethod.GET)
    public Response<SearchOrderResModel> searchOrderByAccountId(@PathVariable("account_id") Long accountId) {
        Response<SearchOrderResModel> response;

        List<OrderDetailModel> orders = orderService.searchOrdersByAccountId(accountId);

        SearchOrderResModel searchResModel = new SearchOrderResModel();
        searchResModel.setOrders(orders);

        response = new Response(ResponseCodeConsts.CODE_ORDER_NORMAL);
        response.setData(searchResModel);
        return response;
    }

    @RequestMapping(value = "/{account_id}/orders/{order_id}", method = RequestMethod.GET)
    public Response<SearchOrderResModel> searchOrderByOrderId(@PathVariable("account_id") Long accountId,
                                                              @PathVariable("order_id") Long orderId) {
        Response<SearchOrderResModel> response;

        OrderDetailModel order = orderService.searchOrderByOrderId(orderId);

        List<OrderDetailModel> orders = new ArrayList<>();
        orders.add(order);
        SearchOrderResModel searchResModel = new SearchOrderResModel();
        searchResModel.setOrders(orders);

        response = new Response(ResponseCodeConsts.CODE_ORDER_NORMAL);
        response.setData(searchResModel);
        return response;
    }


    @RequestMapping(value = "/{account_id}/orders/{order_id}/pay", method = RequestMethod.PUT)
    public Response<Payment> payForOrder(@PathVariable("account_id") Long accountId,
                                         @PathVariable("order_id") Long orderId,
                                         @Valid @RequestBody PayOrderReqModel model) {
        OrderDetailModel orderDetail = orderService.searchOrderByOrderId(orderId);
        BigDecimal totalPrice = orderDetail.getOrder().getTotalPrice();

        BigDecimal redeemPoints = model.getRedeemPoints();
        // 用户想要用积分抵扣。
        if (null != redeemPoints & redeemPoints.intValue() > 0) {
            Boolean result = orderService.reservePoints(accountId, orderId, redeemPoints);
            if (result != null && result) {
                logger.info("此次用于抵扣的积分数:" + redeemPoints + "。");
            } else {
                logger.warn("积分不足或者无法冻结。");
                // 无法用积分支付,将进行全额付款。
                redeemPoints = new BigDecimal("0");
            }
        }

        // 计算扣除积分抵扣部分后需要支付的金额。
        BigDecimal cash = totalPrice.subtract(redeemPoints);

        Order order = orderDetail.getOrder();
        order.setRedeemPoints(redeemPoints);
        order.setCash(cash);
        order.setStatus(OrderStatusEnums.ORDER_STATUS_WAIT_FOR_PAY.getValue());
        orderService.updateOrder(order);

        Response<Payment> response = new Response(ResponseCodeConsts.CODE_ORDER_NORMAL);

        if (cash.intValue() <= 0) {
            logger.info("此订单的费用已全部用积分抵扣,无需额外支付。");
            response.setMessage("本次购物全部使用积分使用。");
        } else {
            Payment payment = orderService.createPayment(accountId, orderId, cash, model.getPaymentMethod());
            if (null != payment) {
                logger.info("账单生成成功,将跳转到第三方支付平台进行支付。");
                // 调用第三方支付平台的接口。
                response.setData(payment);
            }
        }

        return response;
    }


}
