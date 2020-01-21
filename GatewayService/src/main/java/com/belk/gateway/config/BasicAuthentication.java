package com.belk.gateway.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * Represents all information, which has been extracted from JWT (currently only userId)
 */
public class BasicAuthentication implements Authentication {

    /**
	 * 
	 */
	private static final long serialVersionUID = -266270055515144440L;
	private final String userId;

    public BasicAuthentication(String userId) {
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
        throw new UnsupportedOperationException("Basic authentication is always authenticated");
    }

    @Override public String getName() {
        return String.valueOf(userId);
    }
}
