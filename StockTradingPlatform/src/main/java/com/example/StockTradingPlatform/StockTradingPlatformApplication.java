package com.example.StockTradingPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EntityScan(basePackages="com.example")
@EnableJpaRepositories(basePackages="com.example")
@ComponentScan("com.example")
@EnableScheduling
public class StockTradingPlatformApplication {

	public static void main(String[] args) {

		SpringApplication.run(StockTradingPlatformApplication.class, args);
		System.out.println("Successfull");
	}

}
