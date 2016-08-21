package com.gavin.config;


import com.gavin.enums.ExchangeEnums;
import com.gavin.enums.QueueEnums;
import com.gavin.enums.RoutingKeyEnums;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class MessageQueueConfiguration {

    @Bean
    public List<Exchange> declareExchanges() {
        return Arrays.asList(
                new DirectExchange(ExchangeEnums.EXCH_DIRECT_PAYMENT_PAID.getValue(), true, false)
        );
    }

    @Bean
    public List<Queue> declareQueues() {
        return Arrays.asList(
                new Queue(QueueEnums.QUEUE_PAYMENT_PAID.getValue())
        );
    }

    @Bean
    public List<Binding> declareBindings() {
        return Arrays.asList(
                new Binding(QueueEnums.QUEUE_PAYMENT_PAID.getValue(), Binding.DestinationType.QUEUE, ExchangeEnums.EXCH_DIRECT_PAYMENT_PAID.getValue(), RoutingKeyEnums.ROUTINGKEY_PAYMENT_PAID.getValue(), null)
        );
    }
}
