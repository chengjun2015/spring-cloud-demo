package com.gavin.controller;

import com.gavin.domain.payment.Payment;
import com.gavin.service.PaymentService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
@RefreshScope
public class PaymentController {

    @Resource
    private PaymentService paymentService;


    @RequestMapping(value = "/payments", method = RequestMethod.POST)
    public Long createAccount(@RequestParam("order_id") Long orderId,
                              @RequestParam("account_id") Long accountId,
                              @RequestParam("amount") BigDecimal amount,
                              @RequestParam("payment_method") Integer paymentMethod) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAccountId(accountId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);

        paymentService.createPayment(payment);
        return payment.getId();
    }

    @RequestMapping(value = "/payments/{payment_id}", method = RequestMethod.GET)
    public Payment searchPaymentByPaymentId(@PathVariable("payment_id") Long paymentId) {
        return paymentService.searchPaymentById(paymentId);
    }

    @RequestMapping(value = "/payments/{payment_id}/paid", method = RequestMethod.PUT)
    public void feedbackFromThirdParty(@PathVariable("payment_id") Long paymentId,
                                       @RequestParam("flag") Integer flag) {
        // 支付成功
        if (flag == 1) {
            paymentService.succeedInPayment(paymentId);
        }
    }
}
