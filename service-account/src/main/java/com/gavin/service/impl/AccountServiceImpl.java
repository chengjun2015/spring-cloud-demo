package com.gavin.service.impl;

import com.gavin.constant.CacheNameConsts;
import com.gavin.dao.AccountDao;
import com.gavin.domain.account.Account;
import com.gavin.service.AccountService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountDao;

    @Override
    @Transactional
    public void createAccount(Account account) {
        accountDao.create(account);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheNameConsts.CACHE_ACCOUNTS_BY_ID, key = "#accountId")
    public void deleteAccount(Long accountId) {
        accountDao.delete(accountId);
    }

    @Override
    @Cacheable(cacheNames = CacheNameConsts.CACHE_ACCOUNTS_BY_ID, key = "#accountId")
    public Account searchAccountByAccountId(Long accountId) {
        return accountDao.searchById(accountId);
    }
}
