package com.gavin.payload;


import java.io.Serializable;

public class PaidMessage implements Serializable {

    private Long orderId;

    private Boolean paidFlag;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Boolean getPaidFlag() {
        return paidFlag;
    }

    public void setPaidFlag(Boolean paidFlag) {
        this.paidFlag = paidFlag;
    }
}
