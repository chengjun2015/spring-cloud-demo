package com.gavin.dao;

import com.gavin.domain.account.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao {

    int create(Account account);

    int delete(@Param("accountId") Long accountId);

    Account searchById(@Param("accountId") Long accountId);
}
