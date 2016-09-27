package com.gavin.service;

import com.gavin.entity.AddressEntity;
import com.gavin.model.domain.account.AddressModel;

public interface AddressService {

    AddressEntity createAddress(AddressModel addressModel);
}
