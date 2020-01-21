package com.belk.payon.util.model;

import org.springframework.stereotype.Component;

@Component
public class CustomParameters {

	private String source;
	
	public CustomParameters() {}

	public CustomParameters(String source) {
		super();
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	
}
