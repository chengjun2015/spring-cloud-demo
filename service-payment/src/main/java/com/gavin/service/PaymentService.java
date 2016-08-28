package com.gavin.service;


import com.gavin.domain.payment.Payment;

public interface PaymentService {

    void createPayment(Payment payment);

    Payment searchPaymentById(Long paymentId);

    void updatePaidFlag(Long paymentId);

    void succeedInPayment(Long paymentId);
}
