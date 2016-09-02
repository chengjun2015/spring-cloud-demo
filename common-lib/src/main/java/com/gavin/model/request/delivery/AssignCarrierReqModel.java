package com.gavin.model.request.delivery;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AssignCarrierReqModel implements Serializable {

    @JsonProperty("carrier_id")
    @NotNull(message = "carrier_id不能为空")
    private Long orderId;

    @JsonProperty("carrier_id")
    @NotNull(message = "carrier_id不能为空")
    private Long carrierId;

    @JsonProperty("tracking_number")
    @NotNull(message = "tracking_number不能为空")
    private String trackingNumber;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Long carrierId) {
        this.carrierId = carrierId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
