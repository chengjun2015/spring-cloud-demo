package com.gavin.dao;

import com.gavin.domain.order.Item;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDao {

    Long create(Item item);
}
