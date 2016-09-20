package com.gavin.model.request.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class NotifyPaymentReqModel implements Serializable {

    @JsonProperty("result")
    @NotNull(message = "result不能为空")
    private Integer result;
}
