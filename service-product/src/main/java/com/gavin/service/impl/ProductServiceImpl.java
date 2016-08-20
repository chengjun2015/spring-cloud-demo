package com.gavin.service.impl;

import com.gavin.dao.ProductDao;
import com.gavin.domain.product.Product;
import com.gavin.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductDao productDao;

    @Override
    public Long createProduct(Product product) {
        productDao.create(product);
        return product.getId();
    }

    @Override
    public Product searchProductById(Long productId) {
        return productDao.searchById(productId);
    }

    @Override
    public List<Product> searchProductsByIds(Long[] productIds) {
        return productDao.searchByIds(productIds);
    }

    @Override
    public void decreaseStock(Long productId, Integer quantity) {
        productDao.decreaseStock(productId, quantity);
    }

}
