package com.example.springessentialssenders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringEssentialsSendersApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEssentialsSendersApplication.class, args);
    }
}
