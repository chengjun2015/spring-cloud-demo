package com.gavin.model.response.product;

import java.io.Serializable;
import java.util.List;

public class ReserveProductsResModel implements Serializable {

    private List<ProductDetailModel> productDetails;

    public List<ProductDetailModel> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetailModel> productDetails) {
        this.productDetails = productDetails;
    }
}
