package com.customer.rewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@Configuration
@PropertySource("classpath:rewards-threshold.properties")
public class RewardsCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardsCalculatorApplication.class, args);
	}

}
