package com.gavin.service.impl;

import com.gavin.dao.ProductDao;
import com.gavin.domain.order.Item;
import com.gavin.domain.product.Product;
import com.gavin.exception.order.OrderException;
import com.gavin.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public BigDecimal reserve(Item[] items) {

        Long[] productIds = new Long[items.length];
        for (int i = 0; i < items.length; i++) {
            productIds[i] = items[i].getProductId();
        }

        List<Product> products = productDao.searchByIds(productIds);

        Map<Long, Product> productMap = new HashMap<>();
        for (Product product : products) {
            productMap.put(product.getId(), product);
        }

        BigDecimal totalPrice = new BigDecimal(0);

        for (Item item : items) {
            Product product = productMap.get(item.getProductId());

            // 订单中此商品的数量大于库存
            if (item.getQuantity() > product.getStock()) {
                throw new OrderException("商品" + product.getTitle() + "库存不足");
            }

            productDao.decreaseStock(item.getProductId(), item.getQuantity());
            totalPrice = totalPrice.add(new BigDecimal(product.getPrice()).multiply(new BigDecimal(item.getQuantity())));
        }

        return totalPrice;
    }

    @Override
    public void restore(Item[] items) {
        for (Item item : items) {
            productDao.increaseStock(item.getProductId(), item.getQuantity());
        }
    }

}
