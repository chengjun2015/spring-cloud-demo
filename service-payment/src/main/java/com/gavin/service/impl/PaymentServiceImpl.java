package com.gavin.service.impl;


import com.gavin.dao.PaymentDao;
import com.gavin.domain.payment.Payment;
import com.gavin.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

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
    public void updatePaidFlag(Long paymentId) {
        paymentDao.updatePaidFlag(paymentId);
    }

}
