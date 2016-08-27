package com.gavin.service.impl;

import com.gavin.constant.CacheNameConsts;
import com.gavin.dao.AccountDao;
import com.gavin.domain.account.Account;
import com.gavin.service.AccountService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountDao;

    @Override
    public boolean createAccount(Account account) {
        if (accountDao.create(account) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @CacheEvict(cacheNames = CacheNameConsts.CACHE_ACCOUNTS_BY_ID, key = "#accountId")
    public boolean deleteAccount(Long accountId) {
        if (accountDao.delete(accountId) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Cacheable(cacheNames = CacheNameConsts.CACHE_ACCOUNTS_BY_ID, key = "#accountId")
    public Account searchAccountById(Long accountId) {
        return accountDao.searchById(accountId);
    }
}
