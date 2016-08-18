package com.gavin.client.product;

import com.gavin.domain.product.Product;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("product-service")
public interface ProductClient {

    @RequestMapping(value = "/products/{product_id}", method = RequestMethod.GET)
    Product searchProductById(@PathVariable("product_id") Long productId);

    @RequestMapping(value = "/products/{product_id}/reserve", method = RequestMethod.PUT)
    Boolean reserve(@PathVariable("product_id") Long productId, @RequestParam("quantity") Integer quantity);
}
