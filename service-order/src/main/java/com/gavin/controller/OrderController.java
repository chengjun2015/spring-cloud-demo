package com.gavin.controller;

import com.gavin.client.product.ProductClient;
import com.gavin.domain.order.Item;
import com.gavin.domain.order.Order;
import com.gavin.model.order.OrderModel;
import com.gavin.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
    private ProductClient productClient;

    @Resource
    private OrderService orderService;

    @HystrixCommand(fallbackMethod = "reserveFallback")
    @RequestMapping(value = "/{account_id}/orders", method = RequestMethod.POST)
    public Long createOrder(@PathVariable("account_id") Long accountId,
                            @RequestParam("address_id") Long addressId,
                            @RequestBody Item[] items) {
        // 调用product微服务查询库存,预先锁定库存,计算总金额。
        BigDecimal totalPrice = productClient.reserve(items);
        logger.debug("订单总金额: " + totalPrice + "。");

        Order order = new Order();
        order.setAccountId(accountId);
        order.setAddressId(addressId);
        order.setTotalPrice(totalPrice);

        List<Item> itemList = new ArrayList<>();
        Collections.addAll(itemList, items);

        OrderModel orderModel = new OrderModel();
        orderModel.setOrder(order);
        orderModel.setItems(itemList);

        orderService.createOrder(orderModel);

        logger.info("订单已创建, 订单号: " + orderModel.getOrder().getId());

        return orderModel.getOrder().getId();
    }

    public Long reserveFallback(Long accountId,
                                Long addressId,
                                Item[] items) {
        logger.warn("断路器工作中。");
        return -1L;
    }

    @RequestMapping(value = "/orders/{order_id}", method = RequestMethod.GET)
    public OrderModel searchOrderByOrderId(@PathVariable("order_id") Long orderId) {
        return orderService.searchOrderById(orderId);
    }

    @RequestMapping(value = "/{account_id}/orders", method = RequestMethod.GET)
    public List<OrderModel> searchOrderByAccountId(@PathVariable("account_id") Long accountId) {
        return orderService.searchOrdersByAccountId(accountId);
    }

}
