package com.gavin.controller;

import com.gavin.constant.ResponseCodeConsts;
import com.gavin.domain.delivery.Delivery;
import com.gavin.enums.DeliveryStatusEnums;
import com.gavin.model.request.delivery.AssignCarrierReqModel;
import com.gavin.model.response.Response;
import com.gavin.service.DeliveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RefreshScope
public class DeliveryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DeliveryService deliveryService;

    @RequestMapping(value = "/deliveries/{order_id}", method = RequestMethod.GET)
    public Response<Delivery> searchDeliveryByOrderId(@PathVariable("order_id") Long orderId) {
        Delivery delivery = deliveryService.searchDeliveryByOrderId(orderId);

        Response<Delivery> response = new Response(ResponseCodeConsts.CODE_DELIVERY_NORMAL);
        response.setData(delivery);
        return response;
    }

    @RequestMapping(value = "/deliveries/{order_id}/assign", method = RequestMethod.PUT)
    public Response<Delivery> assignCarrier(@PathVariable("order_id") Long orderId,
                                            @Valid @RequestBody AssignCarrierReqModel model) {
        logger.info("订单" + orderId + "已由快递公司" + model.getCarrierId() + "负责配送, 快递单号: " + model.getTrackingNumber() + "。");

        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setCarrierId(model.getCarrierId());
        delivery.setTrackingNumber(model.getTrackingNumber());

        deliveryService.updateCarrierInfo(delivery);

        Response<Delivery> response = new Response(ResponseCodeConsts.CODE_DELIVERY_NORMAL);
        response.setData(delivery);
        return response;
    }

    @RequestMapping(value = "/deliveries/{order_id}/shipped", method = RequestMethod.PUT)
    public Response<Delivery> adviseShipment(@PathVariable("order_id") Long orderId) {
        logger.info("订单号: " + orderId + "已发货。");

        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setStatus(DeliveryStatusEnums.DELIVERY_STATUS_SHIPPED.getValue());

        deliveryService.updateStatus(delivery);

        Response<Delivery> response = new Response(ResponseCodeConsts.CODE_DELIVERY_NORMAL);
        response.setData(delivery);
        return response;
    }

    @RequestMapping(value = "/deliveries/{order_id}/received", method = RequestMethod.PUT)
    public Response<Delivery> adviseReceipt(@PathVariable("order_id") Long orderId) {
        logger.info("订单号: " + orderId + "已收货。");

        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setStatus(DeliveryStatusEnums.DELIVERY_STATUS_RECEIVED.getValue());

        deliveryService.updateStatus(delivery);

        Response<Delivery> response = new Response(ResponseCodeConsts.CODE_DELIVERY_NORMAL);
        response.setData(delivery);
        return response;
    }
}
