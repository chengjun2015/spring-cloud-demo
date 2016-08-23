package com.gavin.controller;

import com.gavin.domain.delivery.Delivery;
import com.gavin.enums.DeliveryStatusEnums;
import com.gavin.service.DeliveryService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RefreshScope
public class DeliveryController {

    @Resource
    private DeliveryService deliveryService;

    @RequestMapping(value = "/deliveries/{order_id}", method = RequestMethod.GET)
    public Delivery searchDeliveryByOrderId(@PathVariable("order_id") Long orderId) {
        return deliveryService.searchDeliveryByOrderId(orderId);
    }

    @RequestMapping(value = "/deliveries/{order_id}/assign", method = RequestMethod.PUT)
    public void assignCarrier(@PathVariable("order_id") Long orderId,
                              @RequestParam("carrier_id") Long carrierId,
                              @RequestParam("tracking_number") String trackingNumber) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setCarrierId(carrierId);
        delivery.setTrackingNumber(trackingNumber);

        deliveryService.updateCarrierInfo(delivery);
    }

    @RequestMapping(value = "/deliveries/{order_id}/shipped", method = RequestMethod.PUT)
    public void adviseShipment(@PathVariable("order_id") Long orderId) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setStatus(DeliveryStatusEnums.DELIVERY_STATUS_SHIPPED.getValue());

        deliveryService.updateStatus(delivery);
    }

    @RequestMapping(value = "/deliveries/{order_id}/received", method = RequestMethod.PUT)
    public void adviseReceipt(@PathVariable("order_id") Long orderId) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setStatus(DeliveryStatusEnums.DELIVERY_STATUS_RECEIVED.getValue());

        deliveryService.updateStatus(delivery);
    }
}
