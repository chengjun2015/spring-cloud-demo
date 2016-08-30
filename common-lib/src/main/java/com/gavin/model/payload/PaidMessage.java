package com.gavin.model.payload;


import java.io.Serializable;

public class PaidMessage implements Serializable {

    private Long orderId;

    private Long accountId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
