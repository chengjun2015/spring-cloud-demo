package com.gavin.enums;


public enum RoutingKeyEnums {

    ROUTINGKEY_PAYMENT_PAID("routingKey.payment.paid"),
    RESOURCE_REQ_OO_HPE_EXCH("resource.request.oo.hpe.exch");

    private String routingKey;

    RoutingKeyEnums(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getValue() {
        return routingKey;
    }

}
