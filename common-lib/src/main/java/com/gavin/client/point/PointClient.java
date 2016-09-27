package com.gavin.client.point;

import com.gavin.model.domain.point.Point;
import com.gavin.model.request.point.ConsumePointReqModel;
import com.gavin.model.request.point.CreatePointReqModel;
import com.gavin.model.request.point.FreezePointReqModel;
import com.gavin.model.request.point.UnfreezePointReqModel;
import com.gavin.model.response.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("point-service")
public interface PointClient {

    @RequestMapping(value = "/points", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Response<Point> createPoints(@RequestBody CreatePointReqModel model);

    @RequestMapping(value = "/points/balance", method = RequestMethod.GET)
    Response<BigDecimal> queryUsablePoints(@RequestParam("account_id") Long accountId);

    @RequestMapping(value = "/points/freeze", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Response reservePoints(@RequestBody FreezePointReqModel model);

    @RequestMapping(value = "/points/unfreeze", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Response unfreezePoints(@RequestBody UnfreezePointReqModel model);

    @RequestMapping(value = "/points/consume", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Response consumePoints(@RequestBody ConsumePointReqModel model);
}
