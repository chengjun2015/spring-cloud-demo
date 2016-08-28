package com.gavin.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderDetailModel implements Serializable {

    private List<ItemDetailModel> itemDetailModels;

    private BigDecimal rewardPoints;

    private BigDecimal totalPrice;

    private String message;

    public List<ItemDetailModel> getItemDetailModels() {
        return itemDetailModels;
    }

    public void setItemDetailModels(List<ItemDetailModel> itemDetailModels) {
        this.itemDetailModels = itemDetailModels;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
