package com.Klaus.be_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeServiceApplication.class, args);
	}

}
