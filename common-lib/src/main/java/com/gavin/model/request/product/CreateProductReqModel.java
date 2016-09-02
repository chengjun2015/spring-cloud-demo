package com.gavin.model.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CreateProductReqModel implements Serializable {

    @JsonProperty("title")
    @NotNull(message = "title不能为空")
    private String title;

    @JsonProperty("category_id")
    @NotNull(message = "category_id不能为空")
    private Long categoryId;

    @JsonProperty("price")
    @NotNull(message = "price不能为空")
    private Float price;

    @JsonProperty("stock")
    @NotNull(message = "stock不能为空")
    private Integer stock;

    @JsonProperty("comment")
    @NotNull(message = "comment不能为空")
    private String comment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
