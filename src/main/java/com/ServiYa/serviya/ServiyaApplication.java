package com.ServiYa.serviya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ServiyaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiyaApplication.class, args);
	}

}
