package com.gavin.dao;

import com.gavin.domain.account.Point;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointDao {

    int create(Point point, @Param("period") int period);

    int replicate(Point point);

    List<Point> searchAvailableByAccountId(@Param("accountId") Long accountId);

    List<Point> searchExpiredByAccountId(@Param("accountId") Long accountId);

    int lockWithOrderId(@Param("ids") List<Long> ids, @Param("orderId") Long orderId);

    int releaseByOrderId(@Param("orderId") Long orderId);

    int updateAmount(@Param("id") Long id, @Param("amount") Integer amount);

    int deleteByOrderId(@Param("orderId") Long orderId);

    int deleteExpiredByAccountId(@Param("accountId") Long accountId);
}
