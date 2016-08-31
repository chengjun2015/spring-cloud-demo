package com.gavin.dao;

import com.gavin.domain.point.PointHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointHistoryDao {

    int create(PointHistory pointHistory);

    List<PointHistory> searchByAccountId(@Param("accountId") Long accountId);
}
