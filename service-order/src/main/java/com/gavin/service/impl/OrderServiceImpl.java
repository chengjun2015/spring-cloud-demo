package com.gavin.service.impl;

import com.gavin.dao.ItemDao;
import com.gavin.dao.OrderDao;
import com.gavin.domain.order.Item;
import com.gavin.domain.order.Order;
import com.gavin.enums.OrderStatusEnums;
import com.gavin.model.order.OrderModel;
import com.gavin.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private ItemDao itemDao;

    @Override
    public Long createOrder(OrderModel orderModel) {
        Order order = orderModel.getOrder();
        order.setStatus(OrderStatusEnums.ORDER_STATUS_CREATED.getValue());
        orderDao.create(order);

        Long orderId = order.getId();

        for (Item item : orderModel.getItems()) {
            item.setOrderId(orderId);
            itemDao.create(item);
        }

        return orderId;
    }

    @Override
    public OrderModel searchOrderById(Long orderId) {
        return orderDao.searchById(orderId);
    }

    @Override
    public List<OrderModel> searchOrdersByAccountId(Long accountId) {
        return orderDao.searchByAccountId(accountId);
    }

    @Override
    public void updateStatus(Order order) {
        orderDao.updateStatus(order);
    }

}
