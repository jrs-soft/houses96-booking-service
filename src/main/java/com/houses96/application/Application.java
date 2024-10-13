package com.houses96.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.houses96")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
