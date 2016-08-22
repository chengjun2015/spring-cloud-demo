package com.gavin.payload;


import java.io.Serializable;

public class PaidMessage implements Serializable {

    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
