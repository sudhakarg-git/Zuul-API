package com.belk.fraudValidation.model;

public class CSUpdate {

	private String merchantRefNumber;
	private String requestId;
	private String originalDecision;
	private String newDecision;
	private String reviewer;
	private String reviewerComments;
	private String queue;
	private String profile;
	private FollowonResult followonResult;
	
	public CSUpdate() {}

	public CSUpdate(String merchantRefNumber, String requestId, String originalDecision, String newDecision,
			String reviewer, String queue, String reviewerComments, String profile, FollowonResult followonResult) {
		super();
		this.merchantRefNumber = merchantRefNumber;
		this.requestId = requestId;
		this.originalDecision = originalDecision;
		this.newDecision = newDecision;
		this.reviewer = reviewer;
		this.queue = queue;
		this.reviewerComments=reviewerComments;
		this.profile = profile;
		this.followonResult = followonResult;
	}

	public String getMerchantRefNumber() {
		return merchantRefNumber;
	}

	public void setMerchantRefNumber(String merchantRefNumber) {
		this.merchantRefNumber = merchantRefNumber;
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

}
