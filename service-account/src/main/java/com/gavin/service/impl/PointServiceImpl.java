package com.gavin.service.impl;

import com.gavin.dao.PointDao;
import com.gavin.dao.PointHistoryDao;
import com.gavin.domain.account.Point;
import com.gavin.domain.account.PointHistory;
import com.gavin.enums.PointActionEnums;
import com.gavin.exception.account.PointException;
import com.gavin.service.PointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("pointService")
public class PointServiceImpl implements PointService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PointDao pointDao;

    @Resource
    private PointHistoryDao pointHistoryDao;

    @Value("${gavin.point.period}")
    private Integer period;

    @Override
    @Transactional
    public void createPoints(Long accountId, Long orderId, BigDecimal amount) {
        pointDao.create(accountId, amount, period);

        // 记录到积分明细表。
        PointHistory pointHistory = new PointHistory();
        pointHistory.setAccountId(accountId);
        pointHistory.setOrderId(orderId);
        pointHistory.setAmount(amount);
        pointHistory.setAction(PointActionEnums.POINT_ACTION_REWARD.getValue());
        pointHistoryDao.create(pointHistory);
    }

    @Override
    @Transactional
    public BigDecimal calculateUsablePoints(Long accountId) {
        return pointDao.searchUsableSumByAccountId(accountId);
    }

    @Override
    @Transactional
    public void reservePoints(Long accountId, Long orderId, BigDecimal amount) {
        BigDecimal newestAmount = pointDao.searchUsableSumByAccountId(accountId);
        newestAmount = newestAmount == null ? new BigDecimal("0") : newestAmount;

        // 账户内最新的可用积分数小与需求的积分数,既余额不足。
        if (newestAmount.compareTo(amount) < 0) {
            throw new PointException("账户" + accountId + "现存的积分数" + newestAmount + "小与需要的积分数" + amount);
        }

        List<Point> points = pointDao.searchUsableByAccountId(accountId);

        List<Long> ids = new ArrayList<>();
        BigDecimal remaining = amount;
        for (Point point : points) {
            if (point.getAmount().compareTo(remaining) < 0) {
                ids.add(point.getId());
                remaining = remaining.subtract(point.getAmount());
            } else {
                // 把一次用不完的积分记录拆分成两条记录。
                Point newPoint = new Point();
                newPoint.setAccountId(point.getAccountId());
                newPoint.setAmount(point.getAmount().subtract(remaining));
                newPoint.setExpireDate(point.getExpireDate());

                pointDao.replicate(newPoint);

                pointDao.updateAmount(point.getId(), remaining);
                ids.add(point.getId());
                break;
            }
        }

        if (!ids.isEmpty()) {
            // 设置这些记录的锁定标志。这样这些积分就无法被其它订单使用,即使过期也不会被清除。
            pointDao.lockWithOrderId(ids, orderId);
        }
    }

    @Override
    @Transactional
    public void restorePoints(Long orderId) {
        // 解除积分记录的锁定标志。
        pointDao.releaseByOrderId(orderId);
    }

    @Override
    @Transactional
    public void consumePoints(Long accountId, Long orderId, BigDecimal amount) {
        pointDao.deleteByOrderId(orderId);

        // 记录到积分明细表。
        PointHistory pointHistory = new PointHistory();
        pointHistory.setAccountId(accountId);
        pointHistory.setOrderId(orderId);
        pointHistory.setAmount(amount);
        pointHistory.setAction(PointActionEnums.POINT_ACTION_CONSUME.getValue());
        pointHistoryDao.create(pointHistory);
    }

    @Override
    @Transactional
    public void clearExpiredPoints(Long accountId) {
        List<Point> expiredPoints = pointDao.searchExpiredByAccountId(accountId);

        if (expiredPoints.isEmpty()) {
            logger.info("账户" + accountId + "内此次没有过期失效的积分。");
            return;
        }

        pointDao.deleteExpiredByAccountId(accountId);

        BigDecimal expiredSum = new BigDecimal(0);
        for (Point point : expiredPoints) {
            expiredSum = expiredSum.add(point.getAmount());
        }
        logger.info("账户" + accountId + "内此次过期失效的积分数: " + expiredSum + "。");

        // 记录到积分明细表。
        PointHistory pointHistory = new PointHistory();
        pointHistory.setAccountId(accountId);
        pointHistory.setAmount(expiredSum);
        pointHistory.setAction(PointActionEnums.POINT_ACTION_EXPIRE.getValue());
        pointHistoryDao.create(pointHistory);
    }

}
