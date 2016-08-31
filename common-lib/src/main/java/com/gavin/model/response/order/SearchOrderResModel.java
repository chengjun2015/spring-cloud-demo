package com.gavin.model.response.order;

import java.io.Serializable;
import java.util.List;

public class SearchOrderResModel implements Serializable {

    private List<OrderDetailModel> orders;

    public List<OrderDetailModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetailModel> orders) {
        this.orders = orders;
    }
}
