package com.belk.empgateway.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.belk.empgateway.dao.UserRepository;
import com.belk.empgateway.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
    private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByName(username);
		UserBuilder builder = null;
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }else {
        	 builder = org.springframework.security.core.userdetails.User.withUsername(username);
             builder.password(new BCryptPasswordEncoder().encode(user.get().getPassword()));
        }
        return builder.build();
	}

}
