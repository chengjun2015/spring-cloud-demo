package com.gavin.model.order;


import com.gavin.domain.order.Item;
import com.gavin.domain.order.Order;

import java.util.List;

public class OrderModel {

    private Order order;

    private List<Item> items;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
