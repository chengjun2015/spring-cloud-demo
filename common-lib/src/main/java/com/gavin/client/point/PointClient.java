package com.gavin.client.point;

import com.gavin.model.request.point.ConsumePointModel;
import com.gavin.model.request.point.CreatePointModel;
import com.gavin.model.request.point.ReservePointModel;
import com.gavin.model.request.point.RestorePointModel;
import com.gavin.model.response.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@FeignClient("account-service")
public interface PointClient {

    @RequestMapping(value = "/{account_id}/points/query", method = RequestMethod.GET)
    Response queryUsablePoints(@PathVariable("account_id") Long accountId);

    @RequestMapping(value = "/{account_id}/points", method = RequestMethod.POST)
    Response createPoints(@PathVariable("account_id") Long accountId,
                          @RequestBody CreatePointModel createPointModel);

    @RequestMapping(value = "/{account_id}/points/reserve", method = RequestMethod.PUT)
    Response reservePoints(@PathVariable("account_id") Long accountId,
                           @RequestBody ReservePointModel reservePointModel);

    @RequestMapping(value = "/{account_id}/points/restore", method = RequestMethod.PUT)
    Response restorePoints(@PathVariable("account_id") Long accountId,
                           @Valid @RequestBody RestorePointModel restorePointModel);

    @RequestMapping(value = "/{account_id}/points/consume", method = RequestMethod.PUT)
    Response consumePoints(@PathVariable("account_id") Long accountId,
                           @Valid @RequestBody ConsumePointModel consumePointModel);

}
