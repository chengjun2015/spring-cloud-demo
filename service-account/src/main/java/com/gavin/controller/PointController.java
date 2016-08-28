package com.gavin.controller;

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

    @RequestMapping(value = "/{account_id}/points", method = RequestMethod.POST)
    public Boolean createPoints(@PathVariable("account_id") Long accountId,
                                @RequestParam("order_id") Long orderId,
                                @RequestParam("amount") BigDecimal amount) {
        boolean result = false;
        try {
            pointService.createPoints(accountId, orderId, amount);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("账户" + accountId + "内由于订单" + orderId + "完成产生的积分: " + amount + "。");
        return result;
    }

    @RequestMapping(value = "/{account_id}/points/query", method = RequestMethod.GET)
    public BigDecimal queryUsablePoints(@PathVariable("account_id") Long accountId) {
        BigDecimal pointsSum = pointService.calculateUsablePoints(accountId);
        logger.info("账户" + accountId + "内目前可用积分: " + pointsSum);
        return pointsSum;
    }

    @RequestMapping(value = "/{account_id}/points/reserve", method = RequestMethod.PUT)
    public Boolean reservePoints(@PathVariable("account_id") Long accountId,
                                 @RequestParam("order_id") Long orderId,
                                 @RequestParam("amount") BigDecimal amount) {
        boolean result = false;
        try {
            pointService.reservePoints(accountId, orderId, amount);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("账户" + accountId + "内此次冻结积分: " + amount);
        return result;
    }

    @RequestMapping(value = "/{account_id}/points/restore", method = RequestMethod.PUT)
    public Boolean restorePoints(@PathVariable("account_id") Long accountId,
                                 @RequestParam("order_id") Long orderId) {
        boolean result = false;
        try {
            pointService.restorePoints(orderId);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("账户" + accountId + "内由订单" + orderId + "冻结的积分已解冻。");
        return result;
    }

    @RequestMapping(value = "/{account_id}/points/consume", method = RequestMethod.PUT)
    public Boolean consumePoints(@PathVariable("account_id") Long accountId,
                                 @RequestParam("order_id") Long orderId,
                                 @RequestParam("amount") BigDecimal amount) {
        boolean result = false;
        try {
            pointService.consumePoints(accountId, orderId, amount);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("账户" + accountId + "内在订单" + orderId + "中使用积分: " + amount + "。");
        return result;
    }
}
