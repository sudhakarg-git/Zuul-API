package com.belk.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SleuthSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SleuthSampleApplication.class, args);
	}

	@Bean
	public RestTemplate getResttemplate() {
		return new RestTemplate();
	}

	

}
