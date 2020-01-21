package com.belk.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.belk.gateway.domain.UsersInfo;
import com.belk.gateway.repository.UsersRepository;

@RestController
public class UserRegistrationController {
	
	@Autowired
	private UsersRepository userRepo;
	
	@PostMapping("/register")
	public UsersInfo registerUser(@RequestBody UsersInfo userDetails) {
		UsersInfo user = userRepo.save(userDetails);
		user.setPassword("");
		return user;
		
	}

}
