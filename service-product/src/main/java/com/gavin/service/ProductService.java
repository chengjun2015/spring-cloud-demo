package com.gavin.service;

import com.gavin.domain.product.Product;

public interface ProductService {

    Long createProduct(Product product);

    Product searchProductById(Long productId);

    void decreaseStock(Long productId, Integer quantity);
}
