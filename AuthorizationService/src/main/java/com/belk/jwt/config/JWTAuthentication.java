package com.belk.jwt.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents all information, which has been extracted from JWT (currently only userId)
 */
public class JWTAuthentication implements Authentication {
    /**
	 * 
	 */
	private static final long serialVersionUID = -266270055515144440L;
	private final String userId;

    public JWTAuthentication(String userId) {
        this.userId = userId;
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
    }

    @Override public Object getCredentials() {
        return null;
    }

    @Override public Object getDetails() {
        return null;
    }

    public String getUserId() {
        return userId;
    }

    @Override public String getPrincipal() {
        return userId;
    }

    @Override public boolean isAuthenticated() {
        return true;
    }

    @Override public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new UnsupportedOperationException("JWT authentication is always authenticated");
    }

    @Override public String getName() {
        return String.valueOf(userId);
    }
}
