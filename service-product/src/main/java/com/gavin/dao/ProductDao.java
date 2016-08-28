package com.gavin.dao;

import com.gavin.domain.product.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao {

    int create(Product product);

    Product searchById(@Param("productId") Long productId);

    List<Product> searchByIds(@Param("productIds") Long[] productIds);

    int decreaseStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);

    int increaseStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);
}
