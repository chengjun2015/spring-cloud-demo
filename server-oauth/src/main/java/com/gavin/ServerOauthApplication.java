package com.gavin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@MapperScan("com.gavin.dao")
@EnableResourceServer
@EnableAuthorizationServer
public class ServerOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerOauthApplication.class, args);
    }

}
