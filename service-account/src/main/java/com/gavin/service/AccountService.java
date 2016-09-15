package com.gavin.service;

import com.gavin.entity.Account;
import com.gavin.model.RestResult;

import java.math.BigDecimal;

public interface AccountService {

    void createAccount(Account account);

    void deleteAccount(Long accountId);

    Account searchAccountByAccountId(Long accountId);

    RestResult<BigDecimal> queryBalance(Long accountId);
}
