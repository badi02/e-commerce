package com.devs.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.devs.ecommerce", "com.devs.ecommerce.mapper"})
@EntityScan("com.devs.ecommerce.model")
@EnableJpaRepositories("com.devs.ecommerce.repositories")
@EnableCaching
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
}
