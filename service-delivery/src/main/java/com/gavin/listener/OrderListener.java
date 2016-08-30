package com.gavin.listener;

import com.gavin.constant.QueueNameConsts;
import com.gavin.domain.delivery.Delivery;
import com.gavin.model.payload.PaidMessage;
import com.gavin.service.DeliveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class OrderListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DeliveryService deliveryService;

    @Transactional
    @RabbitListener(queues = QueueNameConsts.QUEUE_ORDER_PAID)
    public void processPaidMessage(@Payload PaidMessage paidMessage) {
        logger.info("收到支付确认, 订单号: " + paidMessage.getOrderId() + "。");

        Delivery delivery = new Delivery();
        delivery.setOrderId(paidMessage.getOrderId());
        delivery.setAccountId(paidMessage.getAccountId());

        deliveryService.createDelivery(delivery);
    }
}
