package com.belk.fraudValidation.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeName("customerOrder")
@JsonTypeInfo(include=As.WRAPPER_OBJECT, use=Id.NAME)
public class CustomerAcceptOrder extends CustomerOrder{
	
	private Boolean onHold;
	private String reasonCode;

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public Boolean getOnHold() {
		return onHold;
	}

	public void setOnHold(Boolean onHold) {
		this.onHold = onHold;
	}
	
	

}
