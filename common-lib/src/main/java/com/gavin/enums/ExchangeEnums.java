package com.gavin.enums;

public enum ExchangeEnums {

    EXCH_DIRECT_PAYMENT_PAID("exch.direct.payment.paid"),
    RESOURCE_RES_DM_Q(" resource.res.dm.q");

    private String exchangeName;

    ExchangeEnums(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getValue() {
        return exchangeName;
    }
}
