package com.gavin.model.request.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class NotifyPaymentReqModel implements Serializable {

    @JsonProperty("result")
    @NotNull(message = "result不能为空")
    private Integer result;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
