package com.gavin.model.request.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

public class CreatePaymentReqModel implements Serializable {

    @JsonProperty("account_id")
    @NotNull(message = "account_id不能为空")
    private Long accountId;

    @JsonProperty("order_id")
    @NotNull(message = "order_id不能为空")
    private Long orderId;

    @JsonProperty("amount")
    @NotNull(message = "amount不能为空")
    private BigDecimal amount;

    @JsonProperty("payment_method")
    @NotNull(message = "payment_method不能为空")
    private Integer paymentMethod;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
