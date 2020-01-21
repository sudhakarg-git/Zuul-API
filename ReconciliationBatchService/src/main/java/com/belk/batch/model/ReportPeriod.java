package com.belk.batch.model;

import java.util.Date;

public class ReportPeriod {
	
	private Date startDate;
	private Date endDate;
	private String organisationId;
	
	public ReportPeriod() {}
	
	public ReportPeriod(Date startDate, Date endDate, String organisationId) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.organisationId = organisationId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getOrganisationId() {
		return organisationId;
	}
	public void setOrganisationId(String organisationId) {
		this.organisationId = organisationId;
	}
	
	

}
