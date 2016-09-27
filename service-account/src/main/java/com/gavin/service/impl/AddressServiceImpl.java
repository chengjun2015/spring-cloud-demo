package com.gavin.service.impl;

import com.gavin.entity.AddressEntity;
import com.gavin.model.domain.account.AddressModel;
import com.gavin.repository.AddressRepository;
import com.gavin.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressRepository addressRepository;

    @Override
    public AddressEntity createAddress(AddressModel addressModel) {
        AddressEntity addressEntity = new AddressEntity();
        BeanUtils.copyProperties(addressModel, addressEntity);
        return addressRepository.save(addressEntity);
    }
}
