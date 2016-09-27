package com.gavin.client.account;


import com.gavin.model.domain.account.AccountModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("account-service")
public interface AccountClient {

    @RequestMapping(value = "/{account_id}", method = RequestMethod.GET)
    AccountModel searchAccountById(@PathVariable("account_id") Long accountId);

    @RequestMapping(value = "/{account_id}/deposit", method = RequestMethod.PUT)
    Boolean deposit(@PathVariable("account_id") Long accountId, @RequestParam("amount") Float amount);

    @RequestMapping(value = "/{account_id}/pay", method = RequestMethod.PUT)
    Boolean pay(@PathVariable("account_id") Long accountId, @RequestParam("amount") Float amount);
}
