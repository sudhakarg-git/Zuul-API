package com.belk.batch.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("customerOrder")
@JsonTypeInfo(include=As.WRAPPER_OBJECT, use=Id.NAME)
public class CustomerRejectOrder extends CustomerOrder{
	
	private Boolean orderCancelled;
	
	public Boolean getOrderCancelled() {
		return orderCancelled;
	}
	public void setOrderCancelled(Boolean orderCancelled) {
		this.orderCancelled = orderCancelled;
	}
	
}
