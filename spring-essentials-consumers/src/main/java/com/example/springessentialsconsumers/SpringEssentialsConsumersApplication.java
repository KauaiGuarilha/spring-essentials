package com.example.springessentialsconsumers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringEssentialsConsumersApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEssentialsConsumersApplication.class, args);
	}

}
