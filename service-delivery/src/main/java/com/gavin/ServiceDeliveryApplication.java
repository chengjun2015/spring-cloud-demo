package com.gavin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableRabbit
@EnableCaching
@EnableCircuitBreaker
@MapperScan("com.gavin.dao")
public class ServiceDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceDeliveryApplication.class, args);
    }
}
