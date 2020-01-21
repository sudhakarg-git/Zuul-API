package com.belk.empgateway.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.belk.empgateway.dao.UserRepository;
import com.belk.empgateway.exception.DuplicateEmailException;
import com.belk.empgateway.model.User;
import com.belk.empgateway.model.UserInfo;
import com.belk.empgateway.model.UserRegistration;

@Service
public class UserService {

    @Autowired
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRegistration userRegistration) {
        Optional<User> existingUser = userRepository.findByEmail(userRegistration.getEmail());
        if (existingUser.isPresent()) {
            throw new DuplicateEmailException();
        }

        User user = new User(
        		userRegistration.getEmail(),
                userRegistration.getName(),
                passwordEncoder.encode(userRegistration.getPassword())
        );
        userRepository.save(user);
        return user;
    }

    public UserInfo getUserInfo(String userName) {
    	 return userRepository.findByName(userName)
                 .map(user -> new UserInfo(user.getEmail(), user.getName()))
                 .orElseThrow(IllegalStateException::new);
    }
    
    

}
