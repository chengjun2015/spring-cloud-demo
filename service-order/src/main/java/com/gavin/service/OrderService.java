package com.gavin.service;

import com.gavin.domain.order.Item;
import com.gavin.domain.order.Order;
import com.gavin.domain.payment.Payment;
import com.gavin.model.RestResult;
import com.gavin.model.response.order.OrderDetailModel;
import com.gavin.model.response.product.ProductDetailModel;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    RestResult<List<ProductDetailModel>> reserveProducts(Item[] items);

    RestResult<Boolean> reservePoints(Long accountId, Long orderId, BigDecimal amount);

    RestResult<Payment> createPayment(Long accountId, Long orderId, BigDecimal amount, Integer paymentMethod);

    void createOrder(Order order, Item[] items);

    OrderDetailModel searchOrderByOrderId(Long orderId);

    List<OrderDetailModel> searchOrdersByAccountId(Long accountId);

    void updateOrder(Order order);

    void updateStatus(Order order);
}
