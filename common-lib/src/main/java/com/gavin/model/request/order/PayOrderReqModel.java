package com.gavin.model.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

public class PayOrderReqModel implements Serializable {

    @JsonProperty("account_id")
    @NotNull(message = "account_id不能为空。")
    Long accountId;

    @JsonProperty("payment_method")
    @NotNull(message = "payment_method不能为空")
    private Integer paymentMethod;

    @JsonProperty("redeem_points")
    @DecimalMin(value = "1", message = "redeem_points不能小于1。")
    private BigDecimal redeemPoints;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getRedeemPoints() {
        return redeemPoints;
    }

    public void setRedeemPoints(BigDecimal redeemPoints) {
        this.redeemPoints = redeemPoints;
    }
}
