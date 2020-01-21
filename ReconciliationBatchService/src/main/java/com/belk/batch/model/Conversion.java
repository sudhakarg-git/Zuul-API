package com.belk.batch.model;

import java.util.Date;
import java.util.List;


public class Conversion {
	private Long id;
	private String merchantReferenceNumber;
	private Date conversionTime;
	private String requestId;
	private String originalDecision;
	private String newDecision;
	private String reviewer;
	private String reviewerComments;
	private String queue;
	private String profile;
	private List<Note> notes;
	
	
	public Conversion() {}
	
	public Conversion(Long id,Date conversionTime, String merchantReferenceNumber, String requestId, String originalDecision,
			String newDecision, String reviewer, String reviewerComments, List<Note> notes, String queue,
			String profile) {
		super();
		this.id = id;
		this.conversionTime = conversionTime;
		this.merchantReferenceNumber = merchantReferenceNumber;
		this.requestId = requestId;
		this.originalDecision = originalDecision;
		this.newDecision = newDecision;
		this.reviewer = reviewer;
		this.reviewerComments = reviewerComments;
		this.notes = notes;
		this.queue = queue;
		this.profile = profile;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getConversionTime() {
		return conversionTime;
	}

	public void setConversionTime(Date conversionTime) {
		this.conversionTime = conversionTime;
	}

	public String getMerchantReferenceNumber() {
		return merchantReferenceNumber;
	}

	public void setMerchantReferenceNumber(String merchantReferenceNumber) {
		this.merchantReferenceNumber = merchantReferenceNumber;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getOriginalDecision() {
		return originalDecision;
	}

	public void setOriginalDecision(String originalDecision) {
		this.originalDecision = originalDecision;
	}

	public String getNewDecision() {
		return newDecision;
	}

	public void setNewDecision(String newDecision) {
		this.newDecision = newDecision;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public String getReviewerComments() {
		return reviewerComments;
	}

	public void setReviewerComments(String reviewerComments) {
		this.reviewerComments = reviewerComments;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	
	
	
	

}
