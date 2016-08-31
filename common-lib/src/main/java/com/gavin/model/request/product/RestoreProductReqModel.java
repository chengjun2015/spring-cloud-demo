package com.gavin.model.request.product;

import com.gavin.domain.order.Item;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RestoreProductReqModel implements Serializable {

    private Long orderId;

    @NotNull
    @Valid
    private Item[] items;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
