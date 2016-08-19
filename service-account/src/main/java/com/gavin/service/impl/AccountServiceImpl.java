package com.gavin.service.impl;

import com.gavin.dao.AccountDao;
import com.gavin.domain.account.Account;
import com.gavin.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountDao;

    @Override
    public Long createAccount(Account account) {
        accountDao.create(account);
        return account.getId();
    }

    @Override
    public int deleteAccount(Long accountId) {
        return accountDao.delete(accountId);
    }

    @Override
    public Account searchAccountById(Long accountId) {
        return accountDao.searchById(accountId);
    }
}
