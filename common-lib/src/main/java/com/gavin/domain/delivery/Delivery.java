package com.gavin.domain.delivery;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Delivery {

    private Long id;

    private Long orderId;

    private Long accountId;

    private Long carrierId;

    private String trackingNumber;

    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date createdTime;

    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date modifiedTime;

    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date receiptTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Long carrierId) {
        this.carrierId = carrierId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Date getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
    }
}
