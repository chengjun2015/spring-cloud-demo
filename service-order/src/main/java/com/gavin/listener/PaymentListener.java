package com.gavin.listener;

import com.gavin.domain.order.Order;
import com.gavin.enums.OrderStatusEnums;
import com.gavin.payload.PaidMessage;
import com.gavin.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PaymentListener {

    @Resource
    private OrderService orderService;

    @RabbitListener(queues = "queue.payment.paid")
    public void processPaidMessage(@Payload PaidMessage paidMessage) {

        if (paidMessage.getPaidFlag()) {
            Order order = new Order();
            order.setId(paidMessage.getOrderId());
            order.setStatus(OrderStatusEnums.ORDER_STATUS_PAID.getValue());

            orderService.updateStatus(order);
        }
    }

}
