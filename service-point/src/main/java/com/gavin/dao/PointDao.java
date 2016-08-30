package com.gavin.dao;

import com.gavin.domain.account.Point;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PointDao {

    int create(@Param("accountId") Long accountId, @Param("amount") BigDecimal amount, @Param("period") int period);

    int replicate(Point point);

    List<Point> searchUsableByAccountId(@Param("accountId") Long accountId);

    BigDecimal searchUsableSumByAccountId(@Param("accountId") Long accountId);

    List<Point> searchExpiredByAccountId(@Param("accountId") Long accountId);

    int lockWithOrderId(@Param("ids") List<Long> ids, @Param("orderId") Long orderId);

    int releaseByOrderId(@Param("orderId") Long orderId);

    int updateAmount(@Param("id") Long id, @Param("amount") BigDecimal amount);

    int deleteByOrderId(@Param("orderId") Long orderId);

    int deleteExpiredByAccountId(@Param("accountId") Long accountId);
}
