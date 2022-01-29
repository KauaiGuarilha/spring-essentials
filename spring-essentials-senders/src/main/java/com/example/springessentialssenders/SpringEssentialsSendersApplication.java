package com.example.springessentialssenders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@RibbonClient(name = "ms-students-consumer")
@EnableFeignClients
@EnableCircuitBreaker
@SpringBootApplication
public class SpringEssentialsSendersApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEssentialsSendersApplication.class, args);
    }
}
