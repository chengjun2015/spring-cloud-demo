package com.gavin.listener;

import com.gavin.constant.ExchangeNameConsts;
import com.gavin.constant.QueueNameConsts;
import com.gavin.constant.RoutingKeyConsts;
import com.gavin.domain.order.Order;
import com.gavin.enums.OrderStatusEnums;
import com.gavin.payload.PaidMessage;
import com.gavin.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PaymentListener {

    @Resource
    private OrderService orderService;

    @RabbitListener(queues = QueueNameConsts.QUEUE_PAYMENT_PAID)
    @SendTo(ExchangeNameConsts.EXCH_ORDER_PAID + "/" + RoutingKeyConsts.KEY_ORDER_PAID)
    public PaidMessage processPaidMessage(@Payload PaidMessage paidMessage) {
        System.out.println("-------------- PaymentListener - processPaidMessage");

        Order order = new Order();
        order.setId(paidMessage.getOrderId());
        order.setStatus(OrderStatusEnums.ORDER_STATUS_PAID.getValue());

        orderService.updateStatus(order);
        return paidMessage;
    }

}
