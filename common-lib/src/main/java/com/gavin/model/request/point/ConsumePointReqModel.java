package com.gavin.model.request.point;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ConsumePointReqModel implements Serializable {

    @JsonProperty("account_id")
    @NotNull(message = "account_id不能为空")
    private Long accountId;

    @JsonProperty("order_id")
    @NotNull(message = "order_id不能为空。")
    private Long orderId;

    @JsonProperty("amount")
    @NotNull(message = "amount不能为空。")
    @DecimalMin(value = "1", message = "amount不能小于1。")
    private BigDecimal amount;
}
