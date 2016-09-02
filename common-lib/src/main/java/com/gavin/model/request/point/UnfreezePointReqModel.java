package com.gavin.model.request.point;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UnfreezePointReqModel implements Serializable {

    @JsonProperty("account_id")
    @NotNull(message = "account_id不能为空")
    private Long accountId;

    @JsonProperty("order_id")
    @NotNull(message = "order_id不能为空")
    private Long orderId;

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
}
