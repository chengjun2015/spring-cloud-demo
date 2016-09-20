package com.gavin.model.request.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
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
}
