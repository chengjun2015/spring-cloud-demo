package com.gavin.service;

import com.gavin.domain.account.Account;

public interface AccountService {

    boolean createAccount(Account account);

    boolean deleteAccount(Long accountId);

    Account searchAccountById(Long accountId);
}
