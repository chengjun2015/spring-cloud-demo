package com.gavin.controller;

import com.gavin.constant.ResponseCodeConsts;
import com.gavin.entity.AccountEntity;
import com.gavin.model.RestResult;
import com.gavin.model.domain.account.AccountDetailModel;
import com.gavin.model.domain.account.AccountModel;
import com.gavin.model.response.Response;
import com.gavin.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RefreshScope
@Slf4j
public class AccountController {

//    @Value("${account.service.admin}")
//    private String adminUser;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "hasAuthority('AUTH_USER_WRITE')")
    public Response<AccountModel> createAccount(@Valid @RequestBody AccountModel model) {

        AccountEntity accountEntity = accountService.createAccount(model);
        Long accountId = accountEntity.getId();
        log.info("帐号{}已创建。", accountId);

        AccountModel accountModel = new AccountModel();
        BeanUtils.copyProperties(accountEntity, accountModel);

        Response<AccountModel> response = new Response(ResponseCodeConsts.CODE_ACCOUNT_NORMAL);
        response.setMessage("账号" + accountId + "已创建。");
        response.setData(accountModel);
        return response;
    }

    @RequestMapping(value = "/{account_id}", method = RequestMethod.DELETE)
    public Response deleteAccount(@PathVariable("account_id") Long accountId) {
        accountService.deleteAccount(accountId);
        log.info("帐号{}已删除。", accountId);

        Response response = new Response(ResponseCodeConsts.CODE_ACCOUNT_NORMAL);
        response.setMessage("账号" + accountId + "已删除。");
        return response;
    }

    @RequestMapping(value = "/{account_id}", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('AUTH_USER_READ')")
    public Response<AccountModel> searchAccountById(@PathVariable("account_id") Long accountId) {
        AccountModel accountModel = accountService.searchAccountByAccountId(accountId);

        Response<AccountModel> response = new Response(ResponseCodeConsts.CODE_ACCOUNT_NORMAL);
        response.setData(accountModel);

        return response;
    }

    @RequestMapping(value = "/{account_id}/detail", method = RequestMethod.GET)
    public Response<AccountDetailModel> queryAccountDetailById(@PathVariable("account_id") Long accountId) {
        AccountModel accountModel = accountService.searchAccountByAccountId(accountId);

        AccountDetailModel model = new AccountDetailModel();
        model.setAccountModel(accountModel);

        RestResult<BigDecimal> restResult = accountService.queryBalance(accountId);
        if (restResult.isFallback()) {
            log.warn("调用point微服务失败。");
        } else {
            model.setPointsBalance(restResult.getData());
        }

        Response<AccountDetailModel> response = new Response(ResponseCodeConsts.CODE_ACCOUNT_NORMAL);
        response.setData(model);

        return response;
    }

}
