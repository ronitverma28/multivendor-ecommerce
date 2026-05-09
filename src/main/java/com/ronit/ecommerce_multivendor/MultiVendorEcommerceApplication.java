package com.ronit.ecommerce_multivendor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MultiVendorEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiVendorEcommerceApplication.class, args);
	}

}
