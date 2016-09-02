package com.gavin.model.request.point;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

public class FreezePointReqModel implements Serializable {

    @JsonProperty("account_id")
    @NotNull(message = "account_id不能为空")
    private Long accountId;

    @JsonProperty("order_id")
    @NotNull(message = "order_id不能为空")
    private Long orderId;

    @JsonProperty("amount")
    @NotNull(message = "amount不能为空")
    @DecimalMin(value = "1", message = "amount不能小于1。")
    private BigDecimal amount;

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
}
