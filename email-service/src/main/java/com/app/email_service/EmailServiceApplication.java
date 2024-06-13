package com.app.email_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailServiceApplication {

	//email service will consume the order
	public static void main(String[] args) {
		SpringApplication.run(EmailServiceApplication.class, args);
	}

}
