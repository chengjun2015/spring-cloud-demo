package com.gavin.service;

import com.gavin.domain.account.Account;

public interface AccountService {

    Long createAccount(Account account);

    int deleteAccount(Long accountId);

    Account searchAccountById(Long accountId);
}
