package com.gavin.dao;

import com.gavin.domain.payment.Payment;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDao {

    Long create(Payment payment);
}
