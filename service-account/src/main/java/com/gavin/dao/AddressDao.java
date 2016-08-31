package com.gavin.dao;

import com.gavin.domain.account.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao {

    int create(Address address);
}
