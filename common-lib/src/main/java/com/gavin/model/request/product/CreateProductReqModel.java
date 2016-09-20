package com.gavin.model.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
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
}
