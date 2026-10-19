package com.example.StockTradingPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan(basePackages="com.example")
public class StockTradingPlatformApplication {

	public static void main(String[] args) {

		SpringApplication.run(StockTradingPlatformApplication.class, args);
		System.out.println("Successfull");
	}

}
