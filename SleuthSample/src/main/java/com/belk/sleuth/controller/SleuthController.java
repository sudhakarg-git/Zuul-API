package com.belk.sleuth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SleuthController {
	
	private final static Logger LOG = LoggerFactory.getLogger(SleuthController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/home")
	public String getHome() {
		LOG.info("you called Home");  
		return "Hello World!";
	}
	
	@GetMapping("/callHome")
	public String callHome() {
		LOG.info("you called callHome"); 
		return restTemplate.getForObject("http://localhost:8081/home", String.class);
		
	}

}
