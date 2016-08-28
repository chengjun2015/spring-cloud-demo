package com.gavin.service;

import com.gavin.domain.order.Item;
import com.gavin.domain.product.Product;
import com.gavin.exception.order.OrderException;
import com.gavin.model.order.OrderDetailModel;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    Long createProduct(Product product);

    Product searchProductById(Long productId);

    OrderDetailModel reserve(Item[] items) throws OrderException;

    void restore(Item[] items);
}
