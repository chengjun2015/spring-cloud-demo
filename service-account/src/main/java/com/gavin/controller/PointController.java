package com.gavin.controller;

import com.gavin.domain.account.Account;
import com.gavin.service.PointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
public class PointController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PointService pointService;

    @RequestMapping(value = "/{account_id}/points/calculate", method = RequestMethod.GET)
    public BigDecimal calculatePointsSum(@PathVariable("account_id") Long accountId) {
        logger.info("计算账户内积分总数, 账户号: " + accountId);
        return pointService.calcAvailablePointsSum(accountId);
    }

    @RequestMapping(value = "/{account_id}/points/reward", method = RequestMethod.PUT)
    public Boolean rewardPoints(@PathVariable("account_id") Long accountId,
                                @RequestParam("order_id") Long orderId,
                                @RequestParam("amount") Integer amount) {
        boolean result = pointService.rewardPoints(accountId, orderId, amount);
        logger.info("积分已入账, 账户号" + accountId);
        return result;
    }

    @RequestMapping(value = "/{account_id}/points/reserve", method = RequestMethod.PUT)
    public Boolean reservePoints(@PathVariable("account_id") Long accountId,
                                 @RequestParam("order_id") Long orderId,
                                 @RequestParam("amount") Integer amount) {
        boolean result = pointService.reservePoints(accountId, orderId, amount);
        logger.info("积分已冻结, 账户号" + accountId);
        return result;
    }

    @RequestMapping(value = "/{account_id}/points/restore", method = RequestMethod.PUT)
    public Boolean restorePoints(@PathVariable("account_id") Long accountId,
                                 @RequestParam("order_id") Long orderId) {
        boolean result = pointService.restorePoints(orderId);
        logger.info("积分已解冻, 账户号" + accountId);
        return result;
    }

    @RequestMapping(value = "/{account_id}/points/deduct", method = RequestMethod.PUT)
    public Boolean finalizeReservation(@PathVariable("account_id") Long accountId,
                                       @RequestParam("order_id") Long orderId,
                                       @RequestParam("amount") Integer amount) {
        boolean result = pointService.finalizeReservation(accountId, orderId, amount);
        logger.info("积分已永久扣除, 账户号" + accountId);
        return result;
    }
}
