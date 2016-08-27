package com.gavin.service;

import java.math.BigDecimal;

public interface PointService {

    /**
     * 计算账户内可用的积分总额。
     */
    BigDecimal calculateUsablePoints(Long accountId);

    /**
     * 获得返点的积分,同时要记录到积分明细表中。
     */
    void rewardPoints(Long accountId, Long orderId, Integer amount);

    /**
     * 预先扣除积分。如果订单取消,将调用restorePoints方法来恢复这些积分。
     */
    void reservePoints(Long accountId, Long orderId, Integer amount);

    /**
     * 订单中止时调用。恢复预先扣除的积分。
     */
    void restorePoints(Long orderId);

    /**
     * 订单最终完成时调用。
     */
    void consumePoints(Long accountId, Long orderId, Integer amount);

    /**
     * 被定时Job调用用来清理过期积分。
     */
    void clearExpiredPoints(Long accountId);

}
