package com.gavin.service;

import com.gavin.domain.product.Product;

import java.util.List;

public interface ProductService {

    Long createProduct(Product product);

    Product searchProductById(Long productId);

    List<Product> searchProductsByIds(Long[] productIds);

    void decreaseStock(Long productId, Integer quantity);
}
