package com.gavin.dao;

import com.gavin.domain.delivery.Delivery;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryDao {

    Long create(Delivery delivery);
}
