package com.gavin.enums;

public enum OrderStatusEnums {

    ORDER_STATUS_ERROR(-1),
    ORDER_STATUS_CREATED(0),
    ORDER_STATUS_CANCELED(1),
    ORDER_STATUS_EXPIRED(2),
    ORDER_STATUS_PAID(3),
    ORDER_STATUS_SHIPPED(4),
    ORDER_STATUS_RECEIVED(5),
    ORDER_STATUS_COMPLETED(6);

    private Integer orderStatus;

    OrderStatusEnums(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getValue() {
        return orderStatus;
    }
}
