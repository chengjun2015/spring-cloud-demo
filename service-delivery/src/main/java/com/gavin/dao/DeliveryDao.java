package com.gavin.dao;

import com.gavin.domain.delivery.Delivery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryDao {

    Long create(Delivery delivery);

    List<Delivery> searchByOrderId(@Param("orderId") Long orderId);

    void updateCarrierInfo(Delivery delivery);

    void updateStatus(Delivery delivery);
}
