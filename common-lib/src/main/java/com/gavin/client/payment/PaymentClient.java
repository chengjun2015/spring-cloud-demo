package com.gavin.client.payment;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("payment-service")
public interface PaymentClient {

    @RequestMapping(value = "/payments", method = RequestMethod.POST)
    Long createPayment(@RequestParam("account_id") Long accountId,
                       @RequestParam("order_id") Long orderId,
                       @RequestParam("amount") BigDecimal amount,
                       @RequestParam("payment_method") Integer paymentMethod);
}
