package com.gavin.payload;


import java.io.Serializable;

public class PaidMessage implements Serializable {

    private Long paymentId;

    private Boolean paidFlag;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Boolean getPaidFlag() {
        return paidFlag;
    }

    public void setPaidFlag(Boolean paidFlag) {
        this.paidFlag = paidFlag;
    }
}
