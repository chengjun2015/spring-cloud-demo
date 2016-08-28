package com.gavin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
public class ServerTurbineApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerTurbineApplication.class, args);
    }
}
