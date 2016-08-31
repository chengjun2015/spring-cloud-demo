package com.gavin.domain.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item implements Serializable {

    private Long id;

    @JsonProperty("product_id")
    @NotNull(message = "product_id不能为空。")
    private Long productId;

    private Long orderId;

    @JsonProperty("quantity")
    @NotNull(message = "quantity不能为空")
    @Min(value = 1, message = "quantity不能小于1。")
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
