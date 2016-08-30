package com.gavin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableCaching
@MapperScan("com.gavin.dao")
public class ServicePointApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicePointApplication.class, args);
    }
}
