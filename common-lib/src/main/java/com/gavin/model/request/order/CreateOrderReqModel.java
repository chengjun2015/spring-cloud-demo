package com.gavin.model.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gavin.domain.order.Item;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CreateOrderReqModel implements Serializable {

    @JsonProperty("address_id")
    @NotNull(message = "address_id不能为空。")
    private Long addressId;

    @NotNull
    @Valid
    private Item[] items;

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
