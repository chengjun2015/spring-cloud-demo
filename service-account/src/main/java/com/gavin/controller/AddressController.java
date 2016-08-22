package com.gavin.controller;

import com.gavin.domain.account.Address;
import com.gavin.service.AddressService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RefreshScope
public class AddressController {

    @Resource
    private AddressService addressService;

    @RequestMapping(value = "/addresses", method = RequestMethod.POST)
    public Long createAddress(@RequestParam("account_id") Long accountId,
                              @RequestParam("consignee") String consignee,
                              @RequestParam("phone_number") String phoneNumber,
                              @RequestParam("country") String country,
                              @RequestParam("province") String province,
                              @RequestParam("city") String city,
                              @RequestParam("zip_code") String zipCode,
                              @RequestParam("address_line_1") String addressLine1,
                              @RequestParam("address_line_2") String addressLine2,
                              @RequestParam("default_flag") Boolean defaultFlag,
                              @RequestParam("comment") String comment) {
        Address address = new Address();
        address.setAccountId(accountId);
        address.setConsignee(consignee);
        address.setPhoneNumber(phoneNumber);
        address.setCountry(country);
        address.setProvince(province);
        address.setCity(city);
        address.setZipCode(zipCode);
        address.setAddressLine1(addressLine1);
        address.setAddressLine2(addressLine2);
        address.setDefaultFlag(defaultFlag);
        address.setComment(comment);

        addressService.createAddress(address);

        return address.getId();
    }
}
