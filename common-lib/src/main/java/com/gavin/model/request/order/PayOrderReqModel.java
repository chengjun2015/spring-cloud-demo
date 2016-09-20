package com.gavin.model.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
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
}
