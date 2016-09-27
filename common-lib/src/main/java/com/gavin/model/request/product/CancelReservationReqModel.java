package com.gavin.model.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gavin.model.domain.order.Item;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CancelReservationReqModel implements Serializable {

    @JsonProperty("order_id")
    @NotNull(message = "order_id不能为空。")
    private Long orderId;

    @NotNull
    @Valid
    private Item[] items;
}
