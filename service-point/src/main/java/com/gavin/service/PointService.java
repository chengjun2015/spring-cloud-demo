package com.gavin.service;

import com.gavin.domain.point.Point;

import java.math.BigDecimal;

public interface PointService {

    /**
     * 查找一条积分记录。
     */
    Point searchPointByPointId(Long pointId);

    /**
     * 计算账户内可用的积分总额。
     */
    BigDecimal calculateUsablePoints(Long accountId);

    /**
     * 获得返点的积分,同时要记录到积分明细表中。
     */
    Point createPoint(Long accountId, Long orderId, BigDecimal amount);

    /**
     * 预先扣除积分。如果订单取消,将调用restorePoints方法来恢复这些积分。
     */
    void reservePoints(Long accountId, Long orderId, BigDecimal amount);

    /**
     * 订单中止时调用。恢复预先扣除的积分。
     */
    void restorePoints(Long orderId);

    /**
     * 订单最终完成时调用。
     */
    void consumePoints(Long accountId, Long orderId, BigDecimal amount);

    /**
     * 被定时Job调用用来清理过期积分。
     */
    void clearExpiredPoints(Long accountId);

}
