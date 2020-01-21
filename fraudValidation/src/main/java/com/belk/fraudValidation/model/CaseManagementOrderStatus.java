package com.belk.fraudValidation.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "CaseManagementOrderStatus", namespace = "http://reports.cybersource.com/reports/cmos/1.0")
public class CaseManagementOrderStatus {

	@JacksonXmlProperty(localName = "MerchantID")
	private String merchantId;
	@JacksonXmlProperty(localName = "Name")
	private String name;
	@JacksonXmlProperty(localName = "Date")
	private String date;
	@JacksonXmlProperty(localName = "Update")
	private Update update;
	@JacksonXmlProperty(localName = "Version")
	private String version;

	public CaseManagementOrderStatus() {
	}

	public CaseManagementOrderStatus(String merchantId, String name, String date, Update update, String version) {
		super();
		this.merchantId = merchantId;
		this.name = name;
		this.date = date;
		this.update = update;
		this.version = version;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Update getUpdate() {
		return update;
	}

	public void setUpdate(Update update) {
		this.update = update;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
