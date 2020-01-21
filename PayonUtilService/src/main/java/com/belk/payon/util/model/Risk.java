package com.belk.payon.util.model;

import org.springframework.stereotype.Component;

@Component
public class Risk {
	
	private String source;

	public Risk() {}
	
	public Risk(String source) {
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
