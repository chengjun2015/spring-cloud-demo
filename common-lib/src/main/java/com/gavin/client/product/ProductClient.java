package com.gavin.client.product;

import com.gavin.model.request.product.ReserveProductsReqModel;
import com.gavin.model.request.product.CancelReservationReqModel;
import com.gavin.model.response.Response;
import com.gavin.model.response.product.ReserveProductsResModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("product-service")
public interface ProductClient {

    @RequestMapping(value = "/products/reserve", method = RequestMethod.PUT)
    Response<ReserveProductsResModel> reserveProducts(@RequestBody ReserveProductsReqModel model);

    @RequestMapping(value = "/products/cancel", method = RequestMethod.PUT)
    Response cancelReservation(@RequestBody CancelReservationReqModel model);
}
