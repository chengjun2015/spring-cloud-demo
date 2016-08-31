package com.gavin.model.request.product;

import com.gavin.domain.order.Item;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ReserveProductReqModel implements Serializable {

    @NotNull
    @Valid
    private Item[] items;

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
