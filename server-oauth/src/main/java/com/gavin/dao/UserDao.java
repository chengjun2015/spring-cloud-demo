package com.gavin.dao;

import com.gavin.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    User searchByName(@Param("userName") String userName);
}
