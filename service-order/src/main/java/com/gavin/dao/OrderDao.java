package com.gavin.dao;

import com.gavin.domain.order.Order;
import com.gavin.model.order.OrderModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao {

    Long create(Order order);

    OrderModel searchById(@Param("orderId") Long orderId);

    List<OrderModel> searchByAccountId(@Param("accountId") Long accountId);

    void updateStatus(Order order);
}
