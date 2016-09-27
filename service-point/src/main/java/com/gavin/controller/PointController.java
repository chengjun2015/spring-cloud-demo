package com.gavin.controller;

import com.gavin.constant.ResponseCodeConsts;
import com.gavin.exception.account.PointException;
import com.gavin.model.domain.point.Point;
import com.gavin.model.request.point.ConsumePointReqModel;
import com.gavin.model.request.point.CreatePointReqModel;
import com.gavin.model.request.point.FreezePointReqModel;
import com.gavin.model.request.point.UnfreezePointReqModel;
import com.gavin.model.response.Response;
import com.gavin.service.PointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RefreshScope
@Slf4j
public class PointController {

    @Resource
    private PointService pointService;

    @RequestMapping(value = "/points", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response<Point> createPoints(@Valid @RequestBody CreatePointReqModel model) {
        Long accountId = model.getAccountId();
        Long orderId = model.getOrderId();
        BigDecimal amount = model.getAmount();

        Point point = pointService.createPoint(accountId, orderId, amount);
        log.info("账户{}内由于订单{}完成产生的积分数为{}。", accountId, orderId, amount);

        Response<Point> response = new Response<>(ResponseCodeConsts.CODE_POINT_NORMAL);
        response.setData(point);
        return response;
    }

    @RequestMapping(value = "/points/balance", method = RequestMethod.GET)
    @PreAuthorize("#oauth2.hasScope('server') or hasAuthority('AUTH_USER_READ')")
    public Response<BigDecimal> queryUsablePoints(@RequestParam("account_id") Long accountId) {
        BigDecimal pointsSum = pointService.calculateUsablePoints(accountId);
        log.info("账户{}中目前可用积分数为{}", accountId, pointsSum);

        Response<BigDecimal> response = new Response(ResponseCodeConsts.CODE_POINT_NORMAL);
        response.setData(pointsSum);
        return response;
    }

    @RequestMapping(value = "/points/freeze", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response reservePoints(@Valid @RequestBody FreezePointReqModel model) {
        Long accountId = model.getAccountId();
        Long orderId = model.getOrderId();
        BigDecimal amount = model.getAmount();

        Response response;
        try {
            pointService.reservePoints(accountId, orderId, amount);
            log.info("由于订单{}，账户{}内此次冻结积分{}。", orderId, accountId, amount);

            response = new Response(ResponseCodeConsts.CODE_POINT_NORMAL);
            response.setMessage("积分扣除成功,此次扣除" + amount + "。");
        } catch (PointException e) {
            log.warn(e.getMessage());
            response = new Response(ResponseCodeConsts.CODE_POINT_INSUFFICIENT_BALANCE);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @RequestMapping(value = "/points/unfreeze", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response unfreezePoints(@Valid @RequestBody UnfreezePointReqModel model) {
        Long accountId = model.getAccountId();
        Long orderId = model.getOrderId();

        pointService.restorePoints(orderId);
        log.info("账户{}内由于订单{}而冻结的积分已全部解冻。", accountId, orderId);

        Response response = new Response(ResponseCodeConsts.CODE_POINT_NORMAL);
        response.setMessage("冻结的积分已解冻。");

        return response;
    }

    @RequestMapping(value = "/points/consume", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response consumePoints(@Valid @RequestBody ConsumePointReqModel model) {
        Long accountId = model.getAccountId();
        Long orderId = model.getOrderId();
        BigDecimal amount = model.getAmount();

        pointService.consumePoints(accountId, orderId, amount);
        log.info("账户{}内在订单{}中使用积分{}。", accountId, orderId, amount);

        Response response = new Response(ResponseCodeConsts.CODE_POINT_NORMAL);
        response.setMessage("订单" + orderId + "最终使用" + amount + "点积分。");

        return response;
    }
}
