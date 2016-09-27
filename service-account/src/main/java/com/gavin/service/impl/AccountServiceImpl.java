package com.gavin.service.impl;

import com.gavin.client.point.PointClient;
import com.gavin.constant.CacheNameConsts;
import com.gavin.constant.ResponseCodeConsts;
import com.gavin.entity.AccountEntity;
import com.gavin.model.RestResult;
import com.gavin.model.domain.account.AccountModel;
import com.gavin.model.response.Response;
import com.gavin.repository.AccountRepository;
import com.gavin.service.AccountService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PointClient pointClient;

    @Resource
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public AccountEntity createAccount(AccountModel accountModel) {
        AccountEntity accountEntity = new AccountEntity();
        BeanUtils.copyProperties(accountModel, accountEntity);
        return accountRepository.save(accountEntity);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheNameConsts.CACHE_ACCOUNTS_BY_ID, key = "#accountId")
    public void deleteAccount(Long accountId) {
        accountRepository.delete(accountId);
    }

    @Override
    @Cacheable(cacheNames = CacheNameConsts.CACHE_ACCOUNTS_BY_ID, key = "#accountId")
    public AccountModel searchAccountByAccountId(Long accountId) {
        AccountEntity accountEntity = accountRepository.findOne(accountId);
        AccountModel accountModel = new AccountModel();
        BeanUtils.copyProperties(accountEntity, accountModel);
        return accountModel;
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
