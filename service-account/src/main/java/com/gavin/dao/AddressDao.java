package com.gavin.dao;

import com.gavin.domain.account.Account;
import com.gavin.domain.account.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao {

    Long create(Address address);
}
