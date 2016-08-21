package com.gavin.dao;

import com.gavin.domain.payment.Payment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDao {

    Long create(Payment payment);

    Payment searchById(@Param("paymentId") Long paymentId);

    void updatePaidFlag(@Param("paymentId") Long paymentId);
}
