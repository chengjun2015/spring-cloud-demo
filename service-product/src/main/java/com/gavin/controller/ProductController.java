package com.gavin.controller;

import com.gavin.domain.order.Item;
import com.gavin.domain.product.Product;
import com.gavin.exception.order.OrderException;
import com.gavin.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ProductService productService;

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public Long createProduct(@RequestParam(value = "title") String title,
                              @RequestParam(value = "category_id") Long categoryId,
                              @RequestParam(value = "price") Float price,
                              @RequestParam(value = "stock", required = false, defaultValue = "0") Integer stock,
                              @RequestParam(value = "comment", required = false) String comment) {
        Product product = new Product();
        product.setTitle(title);
        product.setCategoryId(categoryId);
        product.setPrice(price);
        product.setStock(stock);
        product.setComment(comment);
        return productService.createProduct(product);
    }

    @RequestMapping(value = "/products/{product_id}", method = RequestMethod.GET)
    public Product searchProductById(@PathVariable("product_id") Long productId) {
        return productService.searchProductById(productId);
    }

    @RequestMapping(value = "/products/reserve", method = RequestMethod.PUT)
    public BigDecimal reserve(@RequestBody Item[] items) {
        try {
            BigDecimal totalPrice = productService.reserve(items);
            logger.info("依据订单相应的库存数已锁定。");
            return totalPrice;
        } catch (OrderException exception) {
            logger.info(exception.getMessage());
            throw exception;
        }
    }

    @RequestMapping(value = "/products/restore", method = RequestMethod.PUT)
    public Boolean restore(@RequestBody Item[] items) {
        productService.restore(items);
        logger.info("由于订单取消或者过期, 锁定的库存数已复原。");
        return true;
    }

}
