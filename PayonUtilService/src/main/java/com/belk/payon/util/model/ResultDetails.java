package com.belk.payon.util.model;

import org.springframework.stereotype.Component;

@Component
public class ResultDetails {

	private String ConnectorTxID3;
	private String ConnectorTxID1;
	private String retrievalReferenceNumber;
	private String authCode;
	private String privateLabelCard;
	private String clearingInstituteName;
	private String cybersourceFraudDecision;
	private String cybersourceResponseCode;
	private String cybersourceResponseText;

	public ResultDetails() {
	}

	public ResultDetails(String connectorTxID3, String connectorTxID1, String retrievalReferenceNumber, String authCode,
			String privateLabelCard, String clearingInstituteName,String cybersourceFraudDecision, String cybersourceResponseCode,
			String cybersourceResponseText) {
		super();
		ConnectorTxID3 = connectorTxID3;
		ConnectorTxID1 = connectorTxID1;
		this.retrievalReferenceNumber = retrievalReferenceNumber;
		this.authCode = authCode;
		this.privateLabelCard = privateLabelCard;
		this.clearingInstituteName = clearingInstituteName;
		this.cybersourceFraudDecision = cybersourceFraudDecision;
		this.cybersourceResponseCode = cybersourceResponseCode;
		this.cybersourceResponseText = cybersourceResponseText;
	}

	public String getConnectorTxID3() {
		return ConnectorTxID3;
	}

	public void setConnectorTxID3(String connectorTxID3) {
		ConnectorTxID3 = connectorTxID3;
	}

	public String getConnectorTxID1() {
		return ConnectorTxID1;
	}

	public void setConnectorTxID1(String connectorTxID1) {
		ConnectorTxID1 = connectorTxID1;
	}

	public String getRetrievalReferenceNumber() {
		return retrievalReferenceNumber;
	}

	public void setRetrievalReferenceNumber(String retrievalReferenceNumber) {
		this.retrievalReferenceNumber = retrievalReferenceNumber;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getPrivateLabelCard() {
		return privateLabelCard;
	}

	public void setPrivateLabelCard(String privateLabelCard) {
		this.privateLabelCard = privateLabelCard;
	}

	public String getCybersourceFraudDecision() {
		return cybersourceFraudDecision;
	}

	public void setCybersourceFraudDecision(String cybersourceFraudDecision) {
		this.cybersourceFraudDecision = cybersourceFraudDecision;
	}

	public String getCybersourceResponseCode() {
		return cybersourceResponseCode;
	}

	public void setCybersourceResponseCode(String cybersourceResponseCode) {
		this.cybersourceResponseCode = cybersourceResponseCode;
	}

	public String getCybersourceResponseText() {
		return cybersourceResponseText;
	}

	public void setCybersourceResponseText(String cybersourceResponseText) {
		this.cybersourceResponseText = cybersourceResponseText;
	}

	public String getClearingInstituteName() {
		return clearingInstituteName;
	}

	public void setClearingInstituteName(String clearingInstituteName) {
		this.clearingInstituteName = clearingInstituteName;
	}

}
