package com.gavin.client.point;

import com.gavin.domain.point.Point;
import com.gavin.model.request.point.ConsumePointReqModel;
import com.gavin.model.request.point.CreatePointReqModel;
import com.gavin.model.request.point.ReservePointReqModel;
import com.gavin.model.request.point.RestorePointReqModel;
import com.gavin.model.response.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;

@FeignClient("point-service")
public interface PointClient {

    @RequestMapping(value = "/{account_id}/points", method = RequestMethod.POST)
    Response<Point> createPoints(@PathVariable("account_id") Long accountId,
                                 @RequestBody CreatePointReqModel model);

    @RequestMapping(value = "/{account_id}/points/query", method = RequestMethod.GET)
    Response<BigDecimal> queryUsablePoints(@PathVariable("account_id") Long accountId);

    @RequestMapping(value = "/{account_id}/points/reserve", method = RequestMethod.PUT)
    Response reservePoints(@PathVariable("account_id") Long accountId,
                           @RequestBody ReservePointReqModel model);

    @RequestMapping(value = "/{account_id}/points/restore", method = RequestMethod.PUT)
    Response restorePoints(@PathVariable("account_id") Long accountId,
                           @RequestBody RestorePointReqModel model);

    @RequestMapping(value = "/{account_id}/points/consume", method = RequestMethod.PUT)
    Response consumePoints(@PathVariable("account_id") Long accountId,
                           @RequestBody ConsumePointReqModel model);

}
