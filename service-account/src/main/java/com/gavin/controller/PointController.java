package com.gavin.controller;

import com.gavin.constant.ResponseCodeConsts;
import com.gavin.exception.account.PointException;

import com.gavin.request.point.ConsumePointReq;
import com.gavin.request.point.CreatePointReq;
import com.gavin.request.point.ReservePointReq;
import com.gavin.request.point.RestorePointReq;
import com.gavin.response.Response;
import com.gavin.service.PointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class PointController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PointService pointService;

    @RequestMapping(value = "/{account_id}/points/query", method = RequestMethod.GET)
    public Response queryUsablePoints(@PathVariable("account_id") Long accountId) {
        BigDecimal pointsSum = pointService.calculateUsablePoints(accountId);
        logger.info("账户" + accountId + "内目前可用积分: " + pointsSum);

        Response response = new Response(ResponseCodeConsts.CODE_POINT_NORMAL);
        response.setData(pointsSum);
        return response;
    }

    @RequestMapping(value = "/{account_id}/points", method = RequestMethod.POST)
    public Response createPoints(@PathVariable("account_id") Long accountId,
                                 @Valid @RequestBody CreatePointReq createPointReq) {
        Long orderId = createPointReq.getOrderId();
        BigDecimal amount = createPointReq.getAmount();

        pointService.createPoints(accountId, orderId, amount);
        logger.info("账户" + accountId + "内由于订单" + orderId + "完成所产生的积分: " + amount + "。");
        return new Response(ResponseCodeConsts.CODE_POINT_NORMAL);
    }

    @RequestMapping(value = "/{account_id}/points/reserve", method = RequestMethod.PUT)
    public Response reservePoints(@PathVariable("account_id") Long accountId,
                                  @Valid @RequestBody ReservePointReq reservePointReq) {
        Long orderId = reservePointReq.getOrderId();
        BigDecimal amount = reservePointReq.getAmount();

        Response response = null;
        try {
            pointService.reservePoints(accountId, orderId, amount);
            logger.info("账户" + accountId + "内此次冻结积分: " + amount);

            response = new Response(ResponseCodeConsts.CODE_POINT_NORMAL);
            response.setMessage("积分扣除成功,此次扣除" + amount + "。");
        } catch (PointException e) {
            logger.warn(e.getMessage());
            response = new Response(ResponseCodeConsts.CODE_POINT_INSUFFICIENT_BALANCE);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @RequestMapping(value = "/{account_id}/points/restore", method = RequestMethod.PUT)
    public Response restorePoints(@PathVariable("account_id") Long accountId,
                                  @Valid @RequestBody RestorePointReq restorePointReq) {
        Long orderId = restorePointReq.getOrderId();

        pointService.restorePoints(orderId);
        logger.info("账户" + accountId + "内由于订单" + orderId + "而冻结的积分已全部解冻。");
        return new Response(ResponseCodeConsts.CODE_POINT_NORMAL);
    }

    @RequestMapping(value = "/{account_id}/points/consume", method = RequestMethod.PUT)
    public Response consumePoints(@PathVariable("account_id") Long accountId,
                                  @Valid @RequestBody ConsumePointReq consumePointReq) {
        Long orderId = consumePointReq.getOrderId();
        BigDecimal amount = consumePointReq.getAmount();

        pointService.consumePoints(accountId, orderId, amount);
        logger.info("账户" + accountId + "内在订单" + orderId + "中使用积分: " + amount + "。");
        return new Response(ResponseCodeConsts.CODE_POINT_NORMAL);
    }
}
