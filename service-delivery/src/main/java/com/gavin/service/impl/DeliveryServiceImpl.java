package com.gavin.service.impl;


import com.gavin.constant.CacheNameConsts;
import com.gavin.dao.DeliveryDao;
import com.gavin.domain.delivery.Delivery;
import com.gavin.enums.DeliveryStatusEnums;
import com.gavin.service.DeliveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("deliveryService")
public class DeliveryServiceImpl implements DeliveryService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DeliveryDao deliveryDao;

    @Override
    public Long createDelivery(Delivery delivery) {
        delivery.setStatus(DeliveryStatusEnums.DELIVERY_STATUS_CREATED.getValue());
        deliveryDao.create(delivery);
        return delivery.getOrderId();
    }

    @Override
    @Cacheable(cacheNames = CacheNameConsts.CACHE_DELIVERIES_BY_ORDERID, key = "#orderId")
    public Delivery searchDeliveryByOrderId(Long orderId) {
        return deliveryDao.searchByOrderId(orderId).get(0);
    }

    @Override
    @CacheEvict(cacheNames = CacheNameConsts.CACHE_DELIVERIES_BY_ORDERID, key = "#delivery.orderId")
    public void updateCarrierInfo(Delivery delivery) {
        delivery.setStatus(DeliveryStatusEnums.DELIVERY_STATUS_ASSIGNED.getValue());
        deliveryDao.updateCarrierInfo(delivery);
    }

    @Override
    @CacheEvict(cacheNames = CacheNameConsts.CACHE_DELIVERIES_BY_ORDERID, key = "#delivery.orderId")
    public void updateStatus(Delivery delivery) {
        deliveryDao.updateStatus(delivery);
    }
}
