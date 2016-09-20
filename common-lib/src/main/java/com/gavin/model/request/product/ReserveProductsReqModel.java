package com.gavin.model.request.product;

import com.gavin.domain.order.Item;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ReserveProductsReqModel implements Serializable {

    @NotNull
    @Valid
    private Item[] items;
}
