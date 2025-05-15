package com.coderhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Preentrega1Application {

	public static void main(String[] args) {
		SpringApplication.run(Preentrega1Application.class, args);
	}

	@Bean
	RestTemplate restTeamplate() {
		return new RestTemplate();
	}
}
