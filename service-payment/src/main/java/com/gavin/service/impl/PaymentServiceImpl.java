package com.gavin.service.impl;


import com.gavin.constant.CacheNameConsts;
import com.gavin.constant.ExchangeNameConsts;
import com.gavin.constant.RoutingKeyConsts;
import com.gavin.dao.PaymentDao;
import com.gavin.domain.payment.Payment;
import com.gavin.payload.PaidMessage;
import com.gavin.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    @Cacheable(cacheNames = CacheNameConsts.CACHE_PAYMENTS_BY_ID, key = "#paymentId")
    public Payment searchPaymentById(Long paymentId) {
        return paymentDao.searchById(paymentId);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @CacheEvict(cacheNames = CacheNameConsts.CACHE_PAYMENTS_BY_ID, key = "#paymentId")
    public void updatePaidFlag(Long paymentId) {
        paymentDao.updatePaidFlag(paymentId);
    }

    @Override
    @Transactional
    public void succeedInPayment(Long paymentId) {
        updatePaidFlag(paymentId);
        Payment payment = searchPaymentById(paymentId);

        PaidMessage paidMessage = new PaidMessage();
        paidMessage.setOrderId(payment.getOrderId());
        paidMessage.setAccountId(payment.getAccountId());

        rabbitTemplate.convertAndSend(ExchangeNameConsts.EXCH_PAYMENT_PAID, RoutingKeyConsts.KEY_PAYMENT_PAID, paidMessage);
        logger.info("Message Queue已发送, 订单编号: " + payment.getOrderId());
    }

}
