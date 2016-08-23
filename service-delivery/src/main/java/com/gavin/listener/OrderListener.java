package com.gavin.listener;

import com.gavin.constant.QueueNameConsts;
import com.gavin.domain.delivery.Delivery;
import com.gavin.domain.order.Order;
import com.gavin.enums.OrderStatusEnums;
import com.gavin.payload.PaidMessage;
import com.gavin.service.DeliveryService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class OrderListener {

    @Resource
    private DeliveryService deliveryService;

    @Transactional
    @RabbitListener(queues = QueueNameConsts.QUEUE_ORDER_PAID)
    public void processPaidMessage(@Payload PaidMessage paidMessage) {
        System.out.println("-------------- OrderListener - processPaidMessage");

        Delivery delivery = new Delivery();
        delivery.setOrderId(paidMessage.getOrderId());
        delivery.setAccountId(paidMessage.getAccountId());

        deliveryService.createDelivery(delivery);
    }
}
