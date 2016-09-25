package com.gavin.controller;

import com.gavin.constant.ResponseCodeConsts;
import com.gavin.exception.account.PointException;
import com.gavin.model.request.point.ConsumePointReqModel;
import com.gavin.model.request.point.CreatePointReqModel;
import com.gavin.model.request.point.FreezePointReqModel;
import com.gavin.model.request.point.UnfreezePointReqModel;
import com.gavin.model.response.Response;
import com.gavin.domain.point.Point;
import com.gavin.service.PointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RefreshScope
public class PointController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PointService pointService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response<Point> createPoints(@Valid @RequestBody CreatePointReqModel model) {
        Long accountId = model.getAccountId();
        Long orderId = model.getOrderId();
        BigDecimal amount = model.getAmount();

        Point point = pointService.createPoint(accountId, orderId, amount);
        logger.info("账户" + accountId + "内由于订单" + orderId + "的完成产生的积分: " + amount + "。");

        Response<Point> response = new Response<>(ResponseCodeConsts.CODE_POINT_NORMAL);
        response.setData(point);
        return response;
    }

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('AUTH_USER_READ')")
    public Response<BigDecimal> queryUsablePoints(@RequestParam("account_id") Long accountId) {
        BigDecimal pointsSum = pointService.calculateUsablePoints(accountId);
        logger.info("账户" + accountId + "内目前可用积分: " + pointsSum + "。");

        Response<BigDecimal> response = new Response(ResponseCodeConsts.CODE_POINT_NORMAL);
        response.setData(pointsSum);
        return response;
    }

    @RequestMapping(value = "/freeze", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response reservePoints(@Valid @RequestBody FreezePointReqModel model) {
        Long accountId = model.getAccountId();
        Long orderId = model.getOrderId();
        BigDecimal amount = model.getAmount();

        Response response;
        try {
            pointService.reservePoints(accountId, orderId, amount);
            logger.info("由于订单" + orderId + ",账户" + accountId + "内此次冻结积分: " + amount + "。");

            response = new Response(ResponseCodeConsts.CODE_POINT_NORMAL);
            response.setMessage("积分扣除成功,此次扣除" + amount + "。");
        } catch (PointException e) {
            logger.warn(e.getMessage());
            response = new Response(ResponseCodeConsts.CODE_POINT_INSUFFICIENT_BALANCE);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @RequestMapping(value = "/unfreeze", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response unfreezePoints(@Valid @RequestBody UnfreezePointReqModel model) {
        Long accountId = model.getAccountId();
        Long orderId = model.getOrderId();

        pointService.restorePoints(orderId);
        logger.info("账户" + accountId + "内由于订单" + orderId + "而冻结的积分已全部解冻。");

        Response response = new Response(ResponseCodeConsts.CODE_POINT_NORMAL);
        response.setMessage("冻结的积分已解冻。");

        return response;
    }

    @RequestMapping(value = "/consume", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response consumePoints(@Valid @RequestBody ConsumePointReqModel model) {
        Long accountId = model.getAccountId();
        Long orderId = model.getOrderId();
        BigDecimal amount = model.getAmount();

        pointService.consumePoints(accountId, orderId, amount);
        logger.info("账户" + accountId + "内在订单" + orderId + "中使用积分: " + amount + "。");

        Response response = new Response(ResponseCodeConsts.CODE_POINT_NORMAL);
        response.setMessage("订单" + orderId + "最终使用" + amount + "点积分。");

        return response;
    }
}
