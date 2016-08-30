package com.gavin.client.product;

import com.gavin.domain.order.Item;
import com.gavin.model.response.order.OrderDetailModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("product-service")
public interface ProductClient {

    @RequestMapping(value = "/products/reserve", method = RequestMethod.PUT)
    OrderDetailModel reserve(@RequestBody Item[] items);

    @RequestMapping(value = "/products/restore", method = RequestMethod.PUT)
    Boolean restore(@RequestBody Item[] items);
}
