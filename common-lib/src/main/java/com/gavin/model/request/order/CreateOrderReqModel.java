package com.gavin.model.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gavin.domain.order.Item;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CreateOrderReqModel implements Serializable {

    @JsonProperty("account_id")
    @NotNull(message = "account_id不能为空。")
    Long accountId;

    @JsonProperty("address_id")
    @NotNull(message = "address_id不能为空。")
    private Long addressId;

    @NotNull
    @Valid
    private Item[] items;
}
