package com.gavin.service.impl;

import com.gavin.client.point.PointClient;
import com.gavin.constant.CacheNameConsts;
import com.gavin.constant.ResponseCodeConsts;
import com.gavin.dao.AccountDao;
import com.gavin.domain.account.Account;
import com.gavin.model.RestResult;
import com.gavin.model.response.Response;
import com.gavin.service.AccountService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PointClient pointClient;

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

    @Override
    @HystrixCommand(fallbackMethod = "queryBalanceFallback")
    public RestResult<BigDecimal> queryBalance(Long accountId) {
        RestResult<BigDecimal> result = new RestResult<>(false);

        Response<BigDecimal> response = pointClient.queryUsablePoints(accountId);
        if (ResponseCodeConsts.CODE_POINT_NORMAL.equals(response.getCode())) {
            logger.info("调用point微服务查询积分成功。");
            result.setData(response.getData());
        } else {
            logger.info("point微服务查询积分失败。");
        }
        return result;
    }

    private RestResult<BigDecimal> queryBalanceFallback(Long accountId) {
        logger.warn("调用queryBalance方法时触发断路器。");
        return new RestResult<>(true);
    }
}
