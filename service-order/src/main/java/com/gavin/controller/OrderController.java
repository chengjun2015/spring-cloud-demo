package com.gavin.controller;

import com.gavin.domain.order.Item;
import com.gavin.domain.order.Order;
import com.gavin.model.order.OrderDetailModel;
import com.gavin.model.order.OrderModel;
import com.gavin.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RefreshScope
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/{account_id}/orders", method = RequestMethod.POST)
    public Long createOrder(@PathVariable("account_id") Long accountId,
                            @RequestParam("address_id") Long addressId,
                            @RequestBody Item[] items) {

        OrderDetailModel orderDetail = orderService.reserveProducts(items);
        // 调用product服务失败。
        if (orderDetail == null) {
            logger.warn("服务暂不可用。");
            return -1L;
        }

        if (!StringUtils.isBlank(orderDetail.getMessage())) {
            logger.warn(orderDetail.getMessage());
            return -1L;
        }

        logger.info("订购的商品全部预约成功。总金额: " + orderDetail.getTotalPrice() + "。");

        Order order = new Order();
        order.setAccountId(accountId);
        order.setAddressId(addressId);
        order.setTotalPrice(orderDetail.getTotalPrice());
        order.setRewardPoints(orderDetail.getRewardPoints());

        List<Item> itemList = new ArrayList<>();
        Collections.addAll(itemList, items);

        OrderModel orderModel = new OrderModel();
        orderModel.setOrder(order);
        orderModel.setItems(itemList);

        orderService.createOrder(orderModel);

        logger.info("订单" + orderModel.getOrder().getId() + "已创建成功。");

        return orderModel.getOrder().getId();
    }

    @RequestMapping(value = "/orders/{order_id}/pay", method = RequestMethod.PUT)
    public Boolean payForOrder(@PathVariable("order_id") Long orderId,
                               @RequestParam("payment_method") Integer paymentMethod,
                               @RequestParam(value = "redeem_points", required = false) BigDecimal redeemPoints) {
        OrderModel orderModel = searchOrderByOrderId(orderId);
        Long accountId = orderModel.getOrder().getAccountId();
        BigDecimal totalPrice = orderModel.getOrder().getTotalPrice();

        if (redeemPoints != null & redeemPoints.intValue() > 0) {
            Boolean result = orderService.reservePoints(accountId, orderId, redeemPoints);
            if (result != null && result) {
                logger.info("积分抵扣成功,此次抵扣的积分数:" + redeemPoints + "。");
            } else {
                // 无法用积分支付,将进行全额付款。
                redeemPoints = new BigDecimal("0");
            }
        }

        BigDecimal remaining = totalPrice.subtract(redeemPoints);

        Order order = orderModel.getOrder();
        order.setRedeemPoints(redeemPoints);
        order.setRemaining(remaining);
        orderService.updateOrder(order);

        if (remaining.intValue() <= 0) {
            logger.info("此订单的费用已全部用积分抵扣,无需额外支付。");
        } else {
            Long paymentId = orderService.createPayment(accountId, orderId, remaining, paymentMethod);
            if (paymentId != null) {
                logger.info("账单生成成功,将跳转到第三方支付平台进行支付。");
                // 调用第三方支付平台的接口。
            }
        }

        return true;
    }

    @RequestMapping(value = "/orders/{order_id}", method = RequestMethod.GET)
    public OrderModel searchOrderByOrderId(@PathVariable("order_id") Long orderId) {
        return orderService.searchOrderByOrderId(orderId);
    }

    @RequestMapping(value = "/{account_id}/orders", method = RequestMethod.GET)
    public List<OrderModel> searchOrderByAccountId(@PathVariable("account_id") Long accountId) {
        return orderService.searchOrdersByAccountId(accountId);
    }

}
