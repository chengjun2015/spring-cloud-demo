package com.gavin.service;

import com.gavin.domain.order.Item;
import com.gavin.domain.order.Order;
import com.gavin.model.order.OrderDetailModel;
import com.gavin.model.order.OrderModel;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    OrderDetailModel reserveProducts(Item[] items);

    Boolean reservePoints(Long accountId, Long orderId, BigDecimal amount);

    Long createPayment(Long accountId, Long orderId, BigDecimal amount, Integer paymentMethod);

    void createOrder(OrderModel orderModel);

    OrderModel searchOrderByOrderId(Long orderId);

    List<OrderModel> searchOrdersByAccountId(Long accountId);

    void updateOrder(Order order);

    void updateStatus(Order order);
}
