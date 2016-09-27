package com.gavin.service;

import com.gavin.entity.AccountEntity;
import com.gavin.model.RestResult;
import com.gavin.model.domain.account.AccountModel;

import java.math.BigDecimal;

public interface AccountService {

    AccountEntity createAccount(AccountModel accountModel);

    void deleteAccount(Long accountId);

    AccountModel searchAccountByAccountId(Long accountId);

    RestResult<BigDecimal> queryBalance(Long accountId);
}
