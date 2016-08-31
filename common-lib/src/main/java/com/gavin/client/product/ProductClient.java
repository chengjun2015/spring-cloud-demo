package com.gavin.client.product;

import com.gavin.model.request.product.ReserveProductReqModel;
import com.gavin.model.request.product.RestoreProductReqModel;
import com.gavin.model.response.Response;
import com.gavin.model.response.product.ReserveProductResModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("product-service")
public interface ProductClient {

    @RequestMapping(value = "/products/reserve", method = RequestMethod.PUT)
    Response<ReserveProductResModel> reserve(@RequestBody ReserveProductReqModel reserveReqModel);

    @RequestMapping(value = "/products/restore", method = RequestMethod.PUT)
    Response restore(@RequestBody RestoreProductReqModel restoreReqModel);
}
