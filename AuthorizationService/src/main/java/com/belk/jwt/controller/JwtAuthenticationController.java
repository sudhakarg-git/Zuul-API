package com.belk.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.belk.jwt.config.JwtTokenUtil;
import com.belk.jwt.exception.InvalidRefreshTokenException;
import com.belk.jwt.model.AccessToken;
import com.belk.jwt.model.RefreshToken;
import com.belk.jwt.model.TokenPair;
import com.belk.jwt.model.User;
import com.belk.jwt.model.UserRegistration;
import com.belk.jwt.service.JwtUserDetailsService;
import com.belk.jwt.service.UserService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserService userDetailsService;
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	/*
	 * @PostMapping("/authenticate") public ResponseEntity<?>
	 * createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
	 * throws Exception { authenticate(authenticationRequest.getUsername(),
	 * authenticationRequest.getPassword()); final User user =
	 * userDetailsService.findUserDetals(authenticationRequest.getUsername()); final
	 * String token = jwtTokenUtil.generateToken(user);
	 * 
	 * return ResponseEntity.ok(new JwtResponse(token,)); }
	 */

	@PostMapping("/register")
	public TokenPair regsiterUser(@RequestBody UserRegistration userRegistration) throws Exception {
		final User user = userDetailsService.registerUser(userRegistration);
		final String jwtToken = jwtTokenUtil.generateToken(user.getName());
		final String refreshToken = jwtUserDetailsService.createRefreshToken(user);
		return new TokenPair(jwtToken, refreshToken);
	}

	@PostMapping("/token/refresh")
	public AccessToken refreshToken(@RequestBody RefreshToken refreshToken) throws Exception {
		return jwtUserDetailsService.refreshAccessToken(refreshToken.getRefreshToken())
				.orElseThrow(InvalidRefreshTokenException::new);
	}

	/*
	 * private void authenticate(String username, String password) throws Exception
	 * { try { authenticationManager.authenticate(new
	 * UsernamePasswordAuthenticationToken(username, password)); } catch
	 * (DisabledException e) { throw new Exception("USER_DISABLED", e); } catch
	 * (BadCredentialsException e) { throw new Exception("INVALID_CREDENTIALS", e);
	 * } }
	 */

}