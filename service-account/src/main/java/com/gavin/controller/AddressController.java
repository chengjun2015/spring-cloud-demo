package com.gavin.controller;

import com.gavin.constant.ResponseCodeConsts;
import com.gavin.entity.AddressEntity;
import com.gavin.model.domain.account.AddressModel;
import com.gavin.model.response.Response;
import com.gavin.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RefreshScope
@Slf4j
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/addresses", method = RequestMethod.POST)
    public Response<AddressModel> createAddress(@Valid @RequestBody AddressModel model) {
        AddressEntity addressEntity = addressService.createAddress(model);
        log.info("地址信息登录成功: {}。" + model.getAddressLine());

        AddressModel addressModel = new AddressModel();
        BeanUtils.copyProperties(addressEntity, addressModel);

        Response<AddressModel> response = new Response(ResponseCodeConsts.CODE_ACCOUNT_NORMAL);
        response.setMessage("地址" + model.getId() + "已创建。");
        response.setData(addressModel);
        return response;
    }
}
