package com.gavin.service.impl;


import com.gavin.constant.ExchangeNameConsts;
import com.gavin.constant.RoutingKeyConsts;
import com.gavin.dao.PaymentDao;
import com.gavin.domain.payment.Payment;
import com.gavin.payload.PaidMessage;
import com.gavin.service.PaymentService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public Long createPayment(Payment payment) {
        paymentDao.create(payment);
        return payment.getId();
    }

    @Override
    public Payment searchPaymentById(Long paymentId) {
        return paymentDao.searchById(paymentId);
    }

    @Override
    @Transactional
    public void succeedInPayment(Long paymentId) {
        paymentDao.updatePaidFlag(paymentId);

        Payment payment = searchPaymentById(paymentId);

        PaidMessage paidMessage = new PaidMessage();
        paidMessage.setOrderId(payment.getOrderId());

        rabbitTemplate.convertAndSend(ExchangeNameConsts.EXCH_PAYMENT_PAID, RoutingKeyConsts.KEY_PAYMENT_PAID, paidMessage);
    }

}
