package com.belk.fraudValidation.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName ="Update")
public class Update {

	@JacksonXmlProperty(localName = "MerchantReferenceNumber")
	private String merchantReferenceNumber;
	@JacksonXmlProperty(localName = "RequestID")
	private String requestId;
	@JacksonXmlProperty(localName = "OriginalDecision")
	private String originalDecision;
	@JacksonXmlProperty(localName = "NewDecision")
	private String newDecision;
	@JacksonXmlProperty(localName = "Reviewer")
	private String reviewer;
	@JacksonXmlProperty(localName = "ReviewerComments")
	private String reviewerComments;
	@JacksonXmlProperty(localName = "Notes")
	private Notes notes;
	@JacksonXmlProperty(localName = "Queue")
	private String queue;
	@JacksonXmlProperty(localName = "Profile")
	private String profile;
	private FollowonResult followonResult;
	
	public Update() {}

	public Update(String merchantReferenceNumber, String requestId, String originalDecision, String newDecision,
			String reviewer, String queue, String reviewerComments, Notes notes,String profile, FollowonResult followonResult) {
		super();
		this.merchantReferenceNumber = merchantReferenceNumber;
		this.requestId = requestId;
		this.originalDecision = originalDecision;
		this.newDecision = newDecision;
		this.reviewer = reviewer;
		this.queue = queue;
		this.reviewerComments=reviewerComments;
		this.notes = notes;
		this.profile = profile;
		this.followonResult = followonResult;
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

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getReviewerComments() {
		return reviewerComments;
	}

	public void setReviewerComments(String reviewerComments) {
		this.reviewerComments = reviewerComments;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public FollowonResult getFollowonResult() {
		return followonResult;
	}

	public void setFollowonResult(FollowonResult followonResult) {
		this.followonResult = followonResult;
	}

	public Notes getNotes() {
		return notes;
	}

	public void setNotes(Notes notes) {
		this.notes = notes;
	}

}
