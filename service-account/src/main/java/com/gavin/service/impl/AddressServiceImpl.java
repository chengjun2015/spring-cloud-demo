package com.gavin.service.impl;

import com.gavin.dao.AddressDao;
import com.gavin.domain.account.Address;
import com.gavin.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressDao addressDao;

    @Override
    public void createAddress(Address address) {
        addressDao.create(address);
    }
}
