package com.belk.gateway.config;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.belk.gateway.domain.UsersInfo;
import com.belk.gateway.repository.UsersRepository;


@Component
public class JwtTokenUtil implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private UsersRepository repository;
	
	public Authentication getAuthentication(String token) {
        // parse the token.
		UsersInfo userInfo=null;
        try {
        	byte[] credDecoded = Base64.getDecoder().decode(token);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            Iterable<UsersInfo> usersList = retreiveAllUsers();
    		
    		for(UsersInfo user: usersList) {
    			if(values[0].equalsIgnoreCase(user.getUsername()))
    			{
    				userInfo=user;
    			}
    		}
        	
        } catch (Exception ex) {
        	throw new UsernameNotFoundException("User not found");
            
        }

        return new BasicAuthentication(userInfo.getUsername());
    }
	
	@Cacheable("users")
	public Iterable<UsersInfo> retreiveAllUsers(){
		return repository.findAll();
	}
}

