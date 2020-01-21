package com.belk.jwt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.belk.jwt.dao.UserRepository;
import com.belk.jwt.exception.DuplicateEmailException;
import com.belk.jwt.model.User;
import com.belk.jwt.model.UserInfo;
import com.belk.jwt.model.UserRegistration;

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
                .map(user -> new UserInfo(user.getName()))
                .orElseThrow(IllegalStateException::new);
    }
    
    

}
