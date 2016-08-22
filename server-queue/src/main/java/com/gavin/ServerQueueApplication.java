package com.gavin;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class ServerQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerQueueApplication.class, args);
    }
}
