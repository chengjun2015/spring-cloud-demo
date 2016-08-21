package com.gavin.enums;


public enum OrderStatusEnums {

    ORDER_STATUS_EXPIRED(-1),
    ORDER_STATUS_CANCELED(0),
    ORDER_STATUS_CREATED(1),
    ORDER_STATUS_PAID(2),
    ORDER_STATUS_DESPATCHED(3),
    ORDER_STATUS_RECEIVED(4),
    ORDER_STATUS_COMPLETED(9);

    private Integer orderStatus;

    OrderStatusEnums(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getValue() {
        return orderStatus;
    }
}
