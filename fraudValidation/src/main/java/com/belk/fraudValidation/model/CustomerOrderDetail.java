package com.belk.fraudValidation.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("customerOrder")
@JsonTypeInfo(include=As.WRAPPER_OBJECT, use=Id.NAME)
public class CustomerOrderDetail {
	
	private String tcCompanyId;
	private boolean orderConfirmed;
	private String orderNumber;
	private Boolean onHold;
	private Boolean orderCancelled;
	private String orderType;
	private boolean responseStatus;
	private String completedExternally;
	
	public CustomerOrderDetail() {}
	
	public CustomerOrderDetail(String tcCompanyId, boolean orderConfirmed, String orderNumber, Boolean onHold,
			Boolean orderCancelled,String orderType,boolean responseStatus,String completedExternally) {
		super();
		this.tcCompanyId = tcCompanyId;
		this.orderConfirmed = orderConfirmed;
		this.orderNumber = orderNumber;
		this.onHold = onHold;
		this.orderCancelled = orderCancelled;
		this.orderType = orderType;
		this.responseStatus = responseStatus;
		this.completedExternally = completedExternally;
		
	}
	public String getTcCompanyId() {
		return tcCompanyId;
	}
	public void setTcCompanyId(String tcCompanyId) {
		this.tcCompanyId = tcCompanyId;
	}
	public boolean isOrderConfirmed() {
		return orderConfirmed;
	}
	public void setOrderConfirmed(boolean orderConfirmed) {
		this.orderConfirmed = orderConfirmed;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Boolean getOnHold() {
		return onHold;
	}
	public void setOnHold(Boolean onHold) {
		this.onHold = onHold;
	}
	public Boolean getOrderCancelled() {
		return orderCancelled;
	}
	public void setOrderCancelled(Boolean orderCancelled) {
		this.orderCancelled = orderCancelled;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public boolean getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(boolean responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getCompletedExternally() {
		return completedExternally;
	}

	public void setCompletedExternally(String completedExternally) {
		this.completedExternally = completedExternally;
	}
	
}
