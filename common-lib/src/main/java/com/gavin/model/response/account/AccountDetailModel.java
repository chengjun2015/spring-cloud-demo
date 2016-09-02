package com.gavin.model.response.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gavin.domain.account.Account;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountDetailModel implements Serializable {

    private Account account;

    @JsonProperty("points_balance")
    private BigDecimal pointsBalance;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getPointsBalance() {
        return pointsBalance;
    }

    public void setPointsBalance(BigDecimal pointsBalance) {
        this.pointsBalance = pointsBalance;
    }
}
