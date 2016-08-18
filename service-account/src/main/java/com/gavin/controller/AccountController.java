package com.gavin.controller;

import com.gavin.domain.account.Account;
import com.gavin.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RefreshScope
public class AccountController {

    @Value("${account.service.admin}")
    private String adminUser;

    @Resource
    private AccountService accountService;

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public String getAdminUserName() {
        return adminUser;
    }

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

        return accountService.createAccount(account);
    }

    @RequestMapping(value = "/accounts/{account_id}", method = RequestMethod.DELETE)
    public void deleteAccount(@PathVariable("account_id") Long accountId) {
        accountService.deleteAccount(accountId);
    }

    @RequestMapping(value = "/accounts/{account_id}", method = RequestMethod.GET)
    public Account searchAccountById(@PathVariable("account_id") Long accountId) {
        return accountService.searchAccountById(accountId);
    }

    @RequestMapping(value = "/accounts/{account_id}/deposit", method = RequestMethod.PUT)
    public Boolean deposit(@PathVariable("account_id") Long accountId, @RequestParam("amount") Float amount) {
        accountService.increaseBalance(accountId, amount);
        Account account = accountService.searchAccountById(accountId);
        // return "此次充值" + amount + "元,您的余额为" + account.getBalance() + "元";
        return true;
    }

    @RequestMapping(value = "/accounts/{account_id}/pay", method = RequestMethod.PUT)
    public Boolean pay(@PathVariable("account_id") Long accountId, @RequestParam("amount") Float amount) {
        accountService.decreaseBalance(accountId, amount);
        Account account = accountService.searchAccountById(accountId);
        // return "此次消费" + amount + "元,您的余额为" + account.getBalance() + "元";
        return true;
    }

}
