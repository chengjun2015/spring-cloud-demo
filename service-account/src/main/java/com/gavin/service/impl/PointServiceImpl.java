package com.gavin.service.impl;

import com.gavin.dao.PointDao;
import com.gavin.dao.PointHistoryDao;
import com.gavin.domain.account.Point;
import com.gavin.domain.account.PointHistory;
import com.gavin.enums.PointActionEnums;
import com.gavin.service.PointService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("pointService")
public class PointServiceImpl implements PointService {

    @Resource
    private PointDao pointDao;

    @Resource
    private PointHistoryDao pointHistoryDao;

    @Value("${gavin.point.period}")
    private Integer period;

    @Override
    public BigDecimal calcAvailablePointsSum(Long accountId) {
        List<Point> points = pointDao.searchAvailableByAccountId(accountId);

        BigDecimal sum = new BigDecimal(0);
        
        if (points.isEmpty()) {
            return sum;
        }

        for (Point point : points) {
            sum = sum.add(new BigDecimal(point.getAmount()));
        }
        return sum;
    }

    @Override
    public boolean rewardPoints(Long accountId, Long orderId, Integer amount) {
        Point point = new Point();
        point.setAccountId(accountId);
        point.setAmount(amount);
        pointDao.create(point, period);

        // 记录到积分明细表。
        PointHistory pointHistory = new PointHistory();
        pointHistory.setAccountId(accountId);
        pointHistory.setOrderId(orderId);
        pointHistory.setAmount(amount);
        pointHistory.setAction(PointActionEnums.POINT_ACTION_REWARD.getValue());
        pointHistoryDao.create(pointHistory);

        return true;
    }

    @Override
    public boolean reservePoints(Long accountId, Long orderId, Integer amount) {
        List<Point> points = pointDao.searchAvailableByAccountId(accountId);

        List<Long> ids = new ArrayList<>();
        int remaining = amount;

        for (Point point : points) {
            if (point.getAmount() < remaining) {
                ids.add(point.getId());
                remaining = remaining - point.getAmount();
            } else {
                // 把一次用不完的积分记录拆分成两条记录。
                int balance = point.getAmount() - remaining;
                Point newPoint = new Point();
                newPoint.setAccountId(point.getAccountId());
                newPoint.setAmount(balance);
                newPoint.setExpireDate(point.getExpireDate());
                pointDao.replicate(newPoint);

                pointDao.updateAmount(point.getId(), remaining);
                ids.add(point.getId());
                break;
            }
        }
        // 设置这些记录的锁定标志。这样这些积分就无法被其它订单使用,即使过期也不会被清除。
        pointDao.lockWithOrderId(ids, orderId);

        return true;
    }

    @Override
    public boolean restorePoints(Long orderId) {
        // 解除积分记录的锁定标志。
        pointDao.releaseByOrderId(orderId);
        return true;
    }

    @Override
    public boolean finalizeReservation(Long accountId, Long orderId, Integer amount) {

        pointDao.deleteByOrderId(orderId);

        // 记录到积分明细表。
        PointHistory pointHistory = new PointHistory();
        pointHistory.setAccountId(accountId);
        pointHistory.setOrderId(orderId);
        pointHistory.setAmount(amount);
        pointHistory.setAction(PointActionEnums.POINT_ACTION_CONSUME.getValue());
        pointHistoryDao.create(pointHistory);

        return true;
    }

    @Override
    public boolean clearExpiredPoints(Long accountId) {
        pointDao.deleteExpiredByAccountId(accountId);

        List<Point> points = pointDao.searchExpiredByAccountId(accountId);
        BigDecimal sum = new BigDecimal(0);
        for (Point point : points) {
            sum = sum.add(new BigDecimal(point.getAmount()));
        }

        // 记录到积分明细表。
        PointHistory pointHistory = new PointHistory();
        pointHistory.setAccountId(accountId);
        pointHistory.setAmount(sum.intValue());
        pointHistory.setAction(PointActionEnums.POINT_ACTION_EXPIRE.getValue());
        pointHistoryDao.create(pointHistory);
        return false;
    }

}
