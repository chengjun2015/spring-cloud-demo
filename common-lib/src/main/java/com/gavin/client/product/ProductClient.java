package com.gavin.client.product;

import com.gavin.domain.order.Item;
import com.gavin.domain.product.Product;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;

@FeignClient("product-service")
public interface ProductClient {

    @RequestMapping(value = "/products/{product_id}", method = RequestMethod.GET)
    Product searchProductById(@PathVariable("product_id") Long productId);

    @RequestMapping(value = "/products/reserve", method = RequestMethod.PUT)
    BigDecimal reserve(@RequestBody Item[] items);

    @RequestMapping(value = "/products/restore", method = RequestMethod.PUT)
    Boolean restore(@RequestBody Item[] items);
}
