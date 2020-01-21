package com.belk.batch.model;

import java.util.Date;
import java.util.List;

public class ReconciliationReport {
	
	private Links _links;
	private List<Conversion> conversionDetails;
	private Date endTime;
	private String organizationId;
	private Date startTime;
	private String code;
	private String detail;
	private String message;
	
	public ReconciliationReport() {}
	
	public ReconciliationReport(Links _links, List<Conversion> conversionDetails, Date endTime, String organizationId,
			Date startTime,String code,String detail,String message) {
		super();
		this._links = _links;
		this.conversionDetails = conversionDetails;
		this.endTime = endTime;
		this.organizationId = organizationId;
		this.startTime = startTime;
		this.code = code;
		this.detail = detail;
		this.message = message;
	}

	public Links get_links() {
		return _links;
	}

	public void set_links(Links _links) {
		this._links = _links;
	}

	public List<Conversion> getConversionDetails() {
		return conversionDetails;
	}

	public void setConversionDetails(List<Conversion> conversionDetails) {
		this.conversionDetails = conversionDetails;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
