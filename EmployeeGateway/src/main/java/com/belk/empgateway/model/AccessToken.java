package com.belk.empgateway.model;

public class AccessToken {

    private final String jwt;

    public AccessToken(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

}
