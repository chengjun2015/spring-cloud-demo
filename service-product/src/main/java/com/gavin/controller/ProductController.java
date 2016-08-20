package com.gavin.controller;

import com.gavin.domain.product.Product;
import com.gavin.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ProductController {

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

    @RequestMapping(value = "/products/list", method = RequestMethod.GET)
    public List<Product> searchProductsByIds(@RequestBody Long[] productIds) {
        return productService.searchProductsByIds(productIds);
    }

    @RequestMapping(value = "/products/{product_id}/reserve", method = RequestMethod.PUT)
    public Boolean reserve(@PathVariable("product_id") Long productId, @RequestParam("quantity") Integer quantity) {
        productService.decreaseStock(productId, quantity);
        return true;
    }

}
