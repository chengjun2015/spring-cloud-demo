package com.gavin.service.impl;

import com.gavin.entity.Address;
import com.gavin.repository.AddressRepository;
import com.gavin.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressRepository addressRepository;

    @Override
    public void createAddress(Address address) {
        addressRepository.save(address);
    }
}
