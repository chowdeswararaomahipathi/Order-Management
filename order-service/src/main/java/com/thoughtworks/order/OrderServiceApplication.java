package com.thoughtworks.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
@EnableCircuitBreaker
public class OrderServiceApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
		
	}

}
