package com.gavin.service.impl;

import com.gavin.constant.CacheNameConsts;
import com.gavin.dao.ItemDao;
import com.gavin.dao.OrderDao;
import com.gavin.domain.order.Item;
import com.gavin.domain.order.Order;
import com.gavin.enums.OrderStatusEnums;
import com.gavin.model.order.OrderModel;
import com.gavin.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    @Cacheable(cacheNames = CacheNameConsts.CACHE_ORDERS_BY_ID, key = "#orderId")
    public OrderModel searchOrderById(Long orderId) {
        return orderDao.searchById(orderId);
    }

    @Override
    @Cacheable(cacheNames = CacheNameConsts.CACHE_ORDERS_BY_ACCOUNTID, key = "#accountId")
    public List<OrderModel> searchOrdersByAccountId(Long accountId) {
        return orderDao.searchByAccountId(accountId);
    }

    @Override
    @CacheEvict(cacheNames = CacheNameConsts.CACHE_ORDERS_BY_ID, key = "#order.id")
    public void updateStatus(Order order) {
        orderDao.updateStatus(order);
    }
}
