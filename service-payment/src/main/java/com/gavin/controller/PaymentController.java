package com.gavin.controller;

import com.gavin.domain.payment.Payment;
import com.gavin.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
@RefreshScope
public class PaymentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PaymentService paymentService;

    @RequestMapping(value = "/payments", method = RequestMethod.POST)
    public Long createPayment(@RequestParam("account_id") Long accountId,
                              @RequestParam("order_id") Long orderId,
                              @RequestParam("amount") BigDecimal amount,
                              @RequestParam("payment_method") Integer paymentMethod) {
        Long paymentId = null;

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAccountId(accountId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);

        try {
            paymentService.createPayment(payment);
            paymentId = payment.getId();
            logger.info("账单" + paymentId + "已创建成功。");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return paymentId;
    }

    @RequestMapping(value = "/payments/{payment_id}", method = RequestMethod.GET)
    public Payment searchPaymentByPaymentId(@PathVariable("payment_id") Long paymentId) {
        return paymentService.searchPaymentById(paymentId);
    }

    @RequestMapping(value = "/payments/{payment_id}/paid", method = RequestMethod.PUT)
    public void feedbackFromThirdParty(@PathVariable("payment_id") Long paymentId,
                                       @RequestParam("result") Integer result) {
        logger.info("收到第三方支付平台的反馈信息。编号 = " + paymentId + ", 支付结果 = " + result + "。");
        // 支付成功
        if (result == 1) {
            paymentService.succeedInPayment(paymentId);
        }
    }
}
