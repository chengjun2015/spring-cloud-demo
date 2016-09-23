package com.gavin.dao;

import com.gavin.security.DemoUserDetails;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    DemoUserDetails searchByName(@Param("userName") String userName);
}
