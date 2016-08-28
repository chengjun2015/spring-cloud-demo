package com.gavin.client.account;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("account-service")
public interface PointClient {

    @RequestMapping(value = "/{account_id}/points", method = RequestMethod.POST)
    Boolean createPoints(@PathVariable("account_id") Long accountId,
                         @RequestParam("order_id") Long orderId,
                         @RequestParam("amount") BigDecimal amount);

    @RequestMapping(value = "/{account_id}/points/query", method = RequestMethod.GET)
    BigDecimal queryUsablePoints(@PathVariable("account_id") Long accountId);

    @RequestMapping(value = "/{account_id}/points/reserve", method = RequestMethod.PUT)
    Boolean reservePoints(@PathVariable("account_id") Long accountId,
                          @RequestParam("order_id") Long orderId,
                          @RequestParam("amount") BigDecimal amount);

    @RequestMapping(value = "/{account_id}/points/restore", method = RequestMethod.PUT)
    Boolean restorePoints(@PathVariable("account_id") Long accountId,
                          @RequestParam("order_id") Long orderId);

    @RequestMapping(value = "/{account_id}/points/consume", method = RequestMethod.PUT)
    Boolean consumePoints(@PathVariable("account_id") Long accountId,
                          @RequestParam("order_id") Long orderId,
                          @RequestParam("amount") BigDecimal amount);

}
