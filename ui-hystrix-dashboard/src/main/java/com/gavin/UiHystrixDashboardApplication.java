package com.gavin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class UiHystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(UiHystrixDashboardApplication.class, args);
    }
}
