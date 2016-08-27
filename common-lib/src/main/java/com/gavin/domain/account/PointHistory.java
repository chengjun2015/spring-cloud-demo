package com.gavin.domain.account;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PointHistory implements Serializable {

    private Long id;

    private Long accountId;

    private Long orderId;

    private Integer detailFlag;

    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getDetailFlag() {
        return detailFlag;
    }

    public void setDetailFlag(Integer detailFlag) {
        this.detailFlag = detailFlag;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
