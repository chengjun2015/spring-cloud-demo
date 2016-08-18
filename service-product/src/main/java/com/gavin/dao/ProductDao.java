package com.gavin.dao;

import com.gavin.domain.product.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao {

    Long create(Product product);

    Product searchById(@Param("productId") Long productId);

    int decreaseStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);
}
