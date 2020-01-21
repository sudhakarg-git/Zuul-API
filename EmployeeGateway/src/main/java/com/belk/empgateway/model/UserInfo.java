package com.belk.empgateway.model;

public class UserInfo {

    private final String name;
    
    private final String email;

    public UserInfo(String email,String name) {
    	this.email=email;
        this.name = name;
    }

    public String getName() {
        return name;
    }

	public String getEmail() {
		return email;
	}


}
