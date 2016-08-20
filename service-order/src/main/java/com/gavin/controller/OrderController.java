package com.gavin.controller;

import com.gavin.client.product.ProductClient;
import com.gavin.domain.order.Item;
import com.gavin.domain.order.Order;
import com.gavin.model.order.OrderModel;
import com.gavin.service.OrderService;
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

    @Resource
    private ProductClient productClient;

    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/{account_id}/orders", method = RequestMethod.POST)
    public String createOrder(@PathVariable("account_id") Long accountId, @RequestBody Item[] items) {

        // 调用product微服务查询库存,预先锁定库存,计算总金额。
        BigDecimal totalPrice = productClient.reserve(items);

        Order order = new Order();
        order.setAccountId(accountId);
        order.setTotalPrice(totalPrice);

        List<Item> itemList = new ArrayList<>();
        Collections.addAll(itemList, items);

        OrderModel orderModel = new OrderModel();
        orderModel.setOrder(order);
        orderModel.setItems(itemList);

        orderService.createOrder(orderModel);

        return "订单已经创建, 订单号: " + orderModel.getOrder().getId();
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
