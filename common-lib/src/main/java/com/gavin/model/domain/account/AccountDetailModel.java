package com.gavin.model.domain.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AccountDetailModel implements Serializable {

    @JsonProperty("account")
    private AccountModel accountModel;

    @JsonProperty("points_balance")
    private BigDecimal pointsBalance;
}
