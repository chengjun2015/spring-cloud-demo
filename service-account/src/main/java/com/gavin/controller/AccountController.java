package com.gavin.controller;

import com.gavin.constant.ResponseCodeConsts;
import com.gavin.domain.account.Account;
import com.gavin.model.RestResult;
import com.gavin.model.request.account.CreateAccountReqModel;
import com.gavin.model.response.Response;
import com.gavin.model.response.account.AccountDetailModel;
import com.gavin.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RefreshScope
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Value("${account.service.admin}")
//    private String adminUser;

    @Resource
    private AccountService accountService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response<Account> createAccount(@Valid @RequestBody CreateAccountReqModel model) {
        Account account = new Account();
        account.setNickName(model.getNickName());
        account.setPassword(model.getPassword());
        account.setMobilePhone(model.getMobilePhone());
        account.setEmail(model.getEmail());

        accountService.createAccount(account);
        Long accountId = account.getId();
        logger.info("账号" + accountId + "已创建。");

        Response<Account> response = new Response(ResponseCodeConsts.CODE_ACCOUNT_NORMAL);
        response.setMessage("账号" + accountId + "已创建。");
        response.setData(account);
        return response;
    }

    @RequestMapping(value = "/{account_id}", method = RequestMethod.DELETE)
    public Response deleteAccount(@PathVariable("account_id") Long accountId) {
        accountService.deleteAccount(accountId);
        logger.info("账号" + accountId + "已删除。");

        Response response = new Response(ResponseCodeConsts.CODE_ACCOUNT_NORMAL);
        response.setMessage("账号" + accountId + "已删除。");
        return response;
    }

    @RequestMapping(value = "/{account_id}", method = RequestMethod.GET)
    public Response<Account> searchAccountById(@PathVariable("account_id") Long accountId) {
        Account account = accountService.searchAccountByAccountId(accountId);

        Response<Account> response = new Response(ResponseCodeConsts.CODE_ACCOUNT_NORMAL);
        response.setData(account);
        return response;
    }

    @RequestMapping(value = "/{account_id}/detail", method = RequestMethod.GET)
    public Response<AccountDetailModel> queryAccountDetailById(@PathVariable("account_id") Long accountId) {
        Account account = accountService.searchAccountByAccountId(accountId);

        AccountDetailModel model = new AccountDetailModel();
        model.setAccount(account);

        RestResult<BigDecimal> restResult = accountService.queryBalance(accountId);
        if (restResult.isFallback()) {
            logger.warn("调用point微服务失败。");
        } else {
            model.setPointsBalance(restResult.getData());
        }

        Response<AccountDetailModel> response = new Response(ResponseCodeConsts.CODE_ACCOUNT_NORMAL);
        response.setData(model);

        return response;
    }

}
