package com.gavin.service.impl;

import com.gavin.constant.CacheNameConsts;
import com.gavin.dao.AccountDao;
import com.gavin.domain.account.Account;
import com.gavin.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AccountDao accountDao;

    @Override
    public Long createAccount(Account account) {
        accountDao.create(account);
        return account.getId();
    }

    @Override
    @CacheEvict(cacheNames = CacheNameConsts.CACHE_ACCOUNTS_BY_ID, key = "#accountId")
    public int deleteAccount(Long accountId) {
        return accountDao.delete(accountId);
    }

    @Override
    @Cacheable(cacheNames = CacheNameConsts.CACHE_ACCOUNTS_BY_ID, key = "#accountId")
    public Account searchAccountById(Long accountId) {
        return accountDao.searchById(accountId);
    }
}
