
package com.belk.empgateway.service;

import java.util.Optional;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.belk.empgateway.dao.UserRefreshTokenRepository;
import com.belk.empgateway.model.AccessToken;
import com.belk.empgateway.model.User;
import com.belk.empgateway.model.UserRefreshToken;
import com.belk.empgateway.security.JwtTokenUtil;

@Service
public class JwtUserDetailsService {

	
	@Autowired
	private UserRefreshTokenRepository userRefreshTokenRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
    /**
     * @return newly generated access token or nothing, if the refresh token is not valid
     */
    public Optional<AccessToken> refreshAccessToken(String refreshToken) {
        return userRefreshTokenRepository.findByToken(refreshToken)
                .map(userRefreshToken -> new AccessToken(
                		jwtTokenUtil.generateToken(userRefreshToken.getUser().getName())
                ));
    }

    public String createRefreshToken(User user) {
        String token = RandomStringUtils.randomAlphanumeric(128);
        userRefreshTokenRepository.save(new UserRefreshToken(token, user));
        return token;

    }

}
