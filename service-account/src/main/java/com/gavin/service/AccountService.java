package com.gavin.service;

import com.gavin.domain.account.Account;

public interface AccountService {

    void createAccount(Account account);

    void deleteAccount(Long accountId);

    Account searchAccountByAccountId(Long accountId);
}
