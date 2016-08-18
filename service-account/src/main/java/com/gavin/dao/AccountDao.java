package com.gavin.dao;


import com.gavin.domain.account.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao {

    Long create(Account account);

    int delete(@Param("accountId") Long accountId);

    Account searchById(@Param("accountId") Long accountId);

    int increaseBalance(@Param("accountId") Long accountId, @Param("amount") Float amount);

    int decreaseBalance(@Param("accountId") Long accountId, @Param("amount") Float amount);
}
