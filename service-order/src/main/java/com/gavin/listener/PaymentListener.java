package com.gavin.listener;

import com.gavin.constant.ExchangeNameConsts;
import com.gavin.constant.QueueNameConsts;
import com.gavin.constant.RoutingKeyConsts;
import com.gavin.domain.order.Order;
import com.gavin.enums.OrderStatusEnums;
import com.gavin.payload.PaidMessage;
import com.gavin.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PaymentListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OrderService orderService;

    @RabbitListener(queues = QueueNameConsts.QUEUE_PAYMENT_PAID)
    @SendTo(ExchangeNameConsts.EXCH_ORDER_PAID + "/" + RoutingKeyConsts.KEY_ORDER_PAID)
    public PaidMessage processPaidMessage(@Payload PaidMessage paidMessage) {
        logger.info("收到支付确认, 订单号: " + paidMessage.getOrderId() + "。");

        Order order = new Order();
        order.setId(paidMessage.getOrderId());
        order.setStatus(OrderStatusEnums.ORDER_STATUS_PAID.getValue());

        orderService.updateStatus(order);
        return paidMessage;
    }

}
