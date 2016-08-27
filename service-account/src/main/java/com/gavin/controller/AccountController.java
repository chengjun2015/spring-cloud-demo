package com.gavin.controller;

import com.gavin.domain.account.Account;
import com.gavin.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RefreshScope
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Value("${account.service.admin}")
//    private String adminUser;

    @Resource
    private AccountService accountService;

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public Long createAccount(@RequestParam("nick_name") String nickName,
                              @RequestParam("password") String password,
                              @RequestParam(value = "mobile_phone", required = false) String mobilePhone,
                              @RequestParam(value = "email", required = false) String email) {
        Account account = new Account();
        account.setNickName(nickName);
        account.setPassword(password);
        account.setMobilePhone(mobilePhone);
        account.setEmail(email);

        accountService.createAccount(account);
        Long accountId = account.getId();
        logger.info("已创建账户, 账户号: " + accountId);
        return accountId;
    }

    @RequestMapping(value = "/accounts/{account_id}", method = RequestMethod.DELETE)
    public void deleteAccount(@PathVariable("account_id") Long accountId) {
        accountService.deleteAccount(accountId);
        logger.info("已删除账户, 账户号: " + accountId);
    }

    @RequestMapping(value = "/accounts/{account_id}", method = RequestMethod.GET)
    public Account searchAccountById(@PathVariable("account_id") Long accountId) {
        logger.info("查询账户, 账户号: " + accountId);
        return accountService.searchAccountById(accountId);
    }
}
