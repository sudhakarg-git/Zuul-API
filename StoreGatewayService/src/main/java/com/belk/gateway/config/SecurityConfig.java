package com.belk.gateway.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.belk.gateway.domain.UsersInfo;
import com.belk.gateway.repository.UsersRepository;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Autowired
	private UsersRepository usersRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
		return http.authorizeExchange().anyExchange().authenticated().and()
				.authenticationManager(reactiveAuthenticationManager()).httpBasic().and().build();
	}

	@Bean
	ReactiveAuthenticationManager reactiveAuthenticationManager() {
		return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsRepository());
	}

	@Bean
	public MapReactiveUserDetailsService userDetailsRepository() {
		List<UserDetails> userList = new ArrayList<UserDetails>();
		Iterable<UsersInfo> users = usersRepo.findAll();

		for (UsersInfo user : users) {
			User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
			UserDetails userDetail = userBuilder.username(user.getUsername()).password(user.getPassword()).roles("USER").build();
			userList.add(userDetail);
		}
		return new MapReactiveUserDetailsService(userList);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
}
