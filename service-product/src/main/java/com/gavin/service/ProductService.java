package com.gavin.service;

import com.gavin.domain.order.Item;
import com.gavin.domain.product.Product;
import com.gavin.model.response.product.ProductDetailModel;

import java.util.List;

public interface ProductService {

    void createProduct(Product product);

    Product searchProductById(Long productId);

    List<ProductDetailModel> reserve(Item[] items);

    void cancel(Item[] items);
}
