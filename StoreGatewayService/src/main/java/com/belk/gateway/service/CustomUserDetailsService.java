
package com.belk.gateway.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.belk.gateway.domain.UsersInfo;
import com.belk.gateway.repository.UsersRepository;
import org.springframework.security.core.userdetails.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UsersRepository userRepository;

	public CustomUserDetailsService(UsersRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsersInfo user=  userRepository.findByUsername(username);
				
	   if (user == null) {
		   throw new UsernameNotFoundException("User not found");
	   }			
				
		
		List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));

	    return new User(user.getUsername(), user.getPassword(), authorities);
		
	}
	
	

}
