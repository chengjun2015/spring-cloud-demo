package com.gavin.controller;

import com.gavin.constant.ResponseCodeConsts;
import com.gavin.domain.account.Address;
import com.gavin.model.request.account.CreateAddressReqModel;
import com.gavin.model.response.Response;
import com.gavin.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RefreshScope
public class AddressController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AddressService addressService;

    @RequestMapping(value = "/addresses", method = RequestMethod.POST)
    public Response<Address> createAddress(@Valid @RequestBody CreateAddressReqModel model) {
        Address address = new Address();
        address.setAccountId(model.getAccountId());
        address.setConsignee(model.getConsignee());
        address.setPhoneNumber(model.getPhoneNumber());
        address.setCountry(model.getCountry());
        address.setProvince(model.getProvince());
        address.setCity(model.getCity());
        address.setZipCode(model.getZipCode());
        address.setAddressLine(model.getAddressLine());
        address.setDefaultFlag(model.getDefaultFlag());
        address.setComment(model.getComment());

        addressService.createAddress(address);
        logger.info("地址信息登录成功: " + address.getAddressLine() + "。");

        Response<Address> response = new Response(ResponseCodeConsts.CODE_ACCOUNT_NORMAL);
        response.setMessage("地址" + address.getId() + "已创建。");
        response.setData(address);
        return response;
    }
}
