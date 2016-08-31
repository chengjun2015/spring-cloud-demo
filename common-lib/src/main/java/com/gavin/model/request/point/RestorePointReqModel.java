package com.gavin.model.request.point;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RestorePointReqModel implements Serializable {

    @JsonProperty("order_id")
    @NotNull(message = "order_id不能为空")
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
