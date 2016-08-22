package com.gavin.config;


import com.gavin.constant.ExchangeNameConsts;
import com.gavin.constant.QueueNameConsts;
import com.gavin.constant.RoutingKeyConsts;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Configuration
public class MessageQueueConfiguration {

    @Resource
    AmqpAdmin amqpAdmin;

    @Bean
    public List<Exchange> declareExchanges() {
        return Arrays.asList(
                new DirectExchange(ExchangeNameConsts.EXCH_PAYMENT_PAID, false, true),
                new DirectExchange(ExchangeNameConsts.EXCH_ORDER_PAID, false, true)
        );
    }

    @Bean
    public List<Queue> declareQueues() {
        return Arrays.asList(
                new Queue(QueueNameConsts.QUEUE_PAYMENT_PAID, false, false, true),
                new Queue(QueueNameConsts.QUEUE_ORDER_PAID, false, false, true)
        );
    }

    @Bean
    public List<Binding> declareBindings() {
        return Arrays.asList(
                new Binding(QueueNameConsts.QUEUE_PAYMENT_PAID, Binding.DestinationType.QUEUE, ExchangeNameConsts.EXCH_PAYMENT_PAID, RoutingKeyConsts.KEY_PAYMENT_PAID, null),
                new Binding(QueueNameConsts.QUEUE_ORDER_PAID, Binding.DestinationType.QUEUE, ExchangeNameConsts.EXCH_ORDER_PAID, RoutingKeyConsts.KEY_ORDER_PAID, null)
        );
    }

    @Bean
    public Queue configureOnBroker() {
        // configure Queues, Exchanges and Bindings on the broker.
        return amqpAdmin.declareQueue();
    }
}
