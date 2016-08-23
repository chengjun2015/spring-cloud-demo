package com.gavin.enums;

public enum DeliveryStatusEnums {

    DELIVERY_STATUS_CREATED(0),
    DELIVERY_STATUS_ASSIGNED(1),
    DELIVERY_STATUS_SHIPPED(2),
    DELIVERY_STATUS_RECEIVED(3);

    private Integer deliveryStatus;

    DeliveryStatusEnums(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Integer getValue() {
        return deliveryStatus;
    }
}
