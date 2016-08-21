package com.gavin.enums;

public enum QueueEnums {

    QUEUE_PAYMENT_PAID("queue.payment.paid"),
    MONITORING_Q("monitoring.q");

    private String queueName;

    QueueEnums(String queueName) {
        this.queueName = queueName;
    }

    public String getValue() {
        return queueName;
    }
}
