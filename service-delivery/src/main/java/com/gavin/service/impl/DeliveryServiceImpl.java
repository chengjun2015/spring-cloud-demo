package com.gavin.service.impl;


import com.gavin.dao.DeliveryDao;
import com.gavin.domain.delivery.Delivery;
import com.gavin.service.DeliveryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("deliveryService")
public class DeliveryServiceImpl implements DeliveryService {

    @Resource
    private DeliveryDao deliveryDao;

    @Override
    public Long createDelivery(Delivery delivery) {
        deliveryDao.create(delivery);
        return delivery.getId();
    }
}
