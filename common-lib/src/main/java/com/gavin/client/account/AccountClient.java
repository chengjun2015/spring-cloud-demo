package com.gavin.client.account;


import com.gavin.domain.account.Account;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("account-service")
public interface AccountClient {

    @RequestMapping(value = "/accounts/{account_id}", method = RequestMethod.GET)
    Account searchAccountById(@PathVariable("account_id") Long accountId);

    @RequestMapping(value = "/accounts/{account_id}/deposit", method = RequestMethod.PUT)
    Boolean deposit(@PathVariable("account_id") Long accountId, @RequestParam("amount") Float amount);

    @RequestMapping(value = "/accounts/{account_id}/pay", method = RequestMethod.PUT)
    Boolean pay(@PathVariable("account_id") Long accountId, @RequestParam("amount") Float amount);
}
