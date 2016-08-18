package com.gavin.service;

import com.gavin.domain.account.Account;

public interface AccountService {

    Long createAccount(Account account);

    int deleteAccount(Long accountId);

    Account searchAccountById(Long accountId);

    void increaseBalance(Long accountId, Float amount);

    void decreaseBalance(Long accountId, Float amount);
}
