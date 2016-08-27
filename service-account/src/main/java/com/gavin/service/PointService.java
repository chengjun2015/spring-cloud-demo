package com.gavin.service;

import java.math.BigDecimal;

public interface PointService {

    /**
     * 计算账户内可用的积分总额。
     */
    BigDecimal calcAvailablePointsSum(Long accountId);

    /**
     * 获得返点的积分,同时要记录到积分明细表中。
     */
    boolean rewardPoints(Long accountId, Long orderId, Integer amount);

    /**
     * 预先扣除积分。如果订单取消,将调用restorePoints方法来恢复这些积分。
     */
    boolean reservePoints(Long accountId, Long orderId, Integer amount);

    /**
     * 订单中止时调用。恢复预先扣除的积分。
     */
    boolean restorePoints(Long orderId);

    /**
     * 订单最终完成时调用。
     */
    boolean finalizeReservation(Long accountId, Long orderId, Integer amount);

    /**
     * 被定时Job调用用来清理过期积分。
     */
    boolean clearExpiredPoints(Long accountId);

}
