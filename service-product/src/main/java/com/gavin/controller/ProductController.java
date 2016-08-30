package com.gavin.controller;

import com.gavin.domain.order.Item;
import com.gavin.domain.product.Product;
import com.gavin.exception.order.OrderException;
import com.gavin.model.response.order.OrderDetailModel;
import com.gavin.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    public OrderDetailModel reserve(@RequestBody Item[] items) {
        OrderDetailModel orderDetail = null;
        try {
            orderDetail = productService.reserve(items);
            logger.info("订购商品的库存已暂时确保。");
        } catch (OrderException exception) {
            logger.warn(exception.getMessage());
        }
        return orderDetail;
    }

    @RequestMapping(value = "/products/restore", method = RequestMethod.PUT)
    public Boolean restore(@RequestBody Item[] items) {
        boolean result = false;
        try {
            productService.restore(items);
            result = true;
            logger.info("订购商品锁定的库存数已复原。");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
