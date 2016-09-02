package com.gavin.controller;

import com.gavin.constant.ResponseCodeConsts;
import com.gavin.domain.payment.Payment;
import com.gavin.model.request.payment.CreatePaymentReqModel;
import com.gavin.model.request.payment.NotifyPaymentReqModel;
import com.gavin.model.response.Response;
import com.gavin.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RefreshScope
public class PaymentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PaymentService paymentService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response<Payment> createPayment(@Valid @RequestBody CreatePaymentReqModel model) {
        Payment payment = new Payment();
        payment.setOrderId(model.getOrderId());
        payment.setAccountId(model.getAccountId());
        payment.setAmount(model.getAmount());
        payment.setPaymentMethod(model.getPaymentMethod());

        paymentService.createPayment(payment);
        logger.info("账单" + payment.getId() + "已创建。");

        Response<Payment> response = new Response<>(ResponseCodeConsts.CODE_PAYMENT_NORMAL);
        response.setData(payment);
        return response;
    }

    @RequestMapping(value = "/{payment_id}", method = RequestMethod.GET)
    public Response<Payment> searchPaymentByPaymentId(@PathVariable("payment_id") Long paymentId) {
        Payment payment = paymentService.searchPaymentById(paymentId);

        Response<Payment> response = new Response<>(ResponseCodeConsts.CODE_PAYMENT_NORMAL);
        response.setData(payment);
        return response;
    }

    @RequestMapping(value = "/{payment_id}/notify", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void notifyPayment(@PathVariable("payment_id") Long paymentId,
                              @Valid @RequestBody NotifyPaymentReqModel model) {
        logger.info("收到第三方支付平台的反馈信息。编号 = " + paymentId + ", 支付结果 = " + model.getResult() + "。");
        // 支付成功
        if (model.getResult() == 1) {
            paymentService.succeedInPayment(paymentId);
        }
    }

}
