package com.gavin.service;

import com.gavin.domain.delivery.Delivery;

public interface DeliveryService {

    Long createDelivery(Delivery delivery);

    Delivery searchDeliveryByOrderId(Long orderId);

    void updateCarrierInfo(Delivery delivery);

    void updateStatus(Delivery delivery);
}
