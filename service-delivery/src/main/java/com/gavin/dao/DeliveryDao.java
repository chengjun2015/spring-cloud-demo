package com.gavin.dao;

import com.gavin.domain.delivery.Delivery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryDao {

    int create(Delivery delivery);

    Delivery searchByOrderId(@Param("orderId") Long orderId);

    int updateCarrierInfo(Delivery delivery);

    int updateStatus(@Param("delivery") Delivery delivery, @Param("receivedStatus") Integer receivedStatus);
}
