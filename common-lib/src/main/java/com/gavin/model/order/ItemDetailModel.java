package com.gavin.model.order;

import com.gavin.domain.order.Item;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemDetailModel implements Serializable {

    private Item item;

    private BigDecimal rewardPoints;

    private BigDecimal totalPrice;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimal getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(BigDecimal rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
