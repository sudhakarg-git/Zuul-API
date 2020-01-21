package com.belk.fraudValidation.model;

public class FollowonResult {

	private String status;
	private String application;
	private String requestId;
	private String decision;
	private String reasonCode;
	private String rCode;
	private String rFlag;
	private String rMsg;
	
	public FollowonResult() {}

	public FollowonResult(String status, String application, String requestId, String decision, String reasonCode,
			String rCode, String rFlag, String rMsg) {
		super();
		this.status = status;
		this.application = application;
		this.requestId = requestId;
		this.decision = decision;
		this.reasonCode = reasonCode;
		this.rCode = rCode;
		this.rFlag = rFlag;
		this.rMsg = rMsg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getrCode() {
		return rCode;
	}

	public void setrCode(String rCode) {
		this.rCode = rCode;
	}

	public String getrFlag() {
		return rFlag;
	}

	public void setrFlag(String rFlag) {
		this.rFlag = rFlag;
	}

	public String getRMsg() {
		return rMsg;
	}

	public void setRMsg(String rMsg) {
		this.rMsg = rMsg;
	}

}
