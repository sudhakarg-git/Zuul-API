package com.belk.batch.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.belk.batch.exception.CyberSourceException;

@Component
public class DOMHttpSignature {
	
	String gmtDateTime = getdate();
	
	// HTTP GET request
	public String sendGet(String requestHost,String resource,String merchantId,String merchantKeyId,String merchantSecretKey) throws CyberSourceException {

		String url = "https://" + requestHost + resource;
		StringBuffer response = null;

		try {
		/* HTTP connection */
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		/*
		 * Add Request Header :: "v-c-merchant-id" set value to Cybersource Merchant ID
		 * or v-c-merchant-id This ID can be found on EBC portal
		 */
		con.setRequestProperty("v-c-merchant-id", merchantId);
		con.setRequestProperty("v-c-correlation-id", "123");
		// con.setRequestProperty("profile-id", "93B32398-AD51-4CC2-A682-EA3E93614EB1");

		/*
		 * Add Request Header :: "Date" The date and time that the message was
		 * originated from. "HTTP-date" format as defined by RFC7231.
		 */
		
		con.setRequestProperty("date", gmtDateTime);

		/*
		 * Add Request Header :: "Host" Sandbox Host: apitest.cybersource.com Production
		 * Host: api.cybersource.com
		 */
		con.setRequestProperty("Host", requestHost);

		/*
		 * Add Request Header :: "Signature" Signature header contains keyId, algorithm,
		 * headers and signature as paramters method getSignatureHeader() has more
		 * details
		 */
		StringBuilder signatureHeaderValue = getSignatureHeader("GET",merchantKeyId,merchantSecretKey,requestHost,resource,merchantId);
		con.setRequestProperty("Signature", signatureHeaderValue.toString());

		/* HTTP Method GET */
		con.setRequestMethod("GET");

		/* Addition Request Headers */
//        con.setRequestProperty("User-Agent", USER_AGENT);
//        con.setRequestProperty("Content-Type", "application/json");

		/* Establishing HTTP connection */
		int responseCode = con.getResponseCode();
		String responseHeader = con.getHeaderField("v-c-correlation-id");


		/* Reading Response Message */
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		}catch(Exception ex) {
			throw new CyberSourceException(ex.getMessage());
		}

		return response.toString();

	}

	public String getdate() {
		/* This Method returns Date in GMT format as defined by RFC7231. */
		return (DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT"))));
	}

	public StringBuilder getSignatureHeader(String httpMethod,String merchantKeyId,String merchantSecretKey,String requestHost,String resource,String merchantId) throws Exception {

		/*
		 * This method return SignatureHeader Value that contains following paramters
		 * keyid -- Merchant ID obtained from EBC portal algorithm -- Should have value
		 * as "HmacSHA256" headers -- List of all header name passed in the Signature
		 * paramter below String getHeaders = "host date (request-target)" + " " +
		 * "v-c-merchant-id"; String postHeaders =
		 * "host date (request-target) digest v-c-merchant-id"; Note: Digest is not
		 * passed for GET calls signature -- Signature header has paramter called
		 * signature Paramter 'Signature' must contain all the paramters mentioned in
		 * header above in given order
		 */

		StringBuilder signatureHeaderValue = new StringBuilder();

		/* KeyId is the key obtained from EBC */
		signatureHeaderValue.append("keyid=\"" + merchantKeyId + "\"");

		/* Algorithm should be always HmacSHA256 for http signature */
		signatureHeaderValue.append(", algorithm=\"HmacSHA256\"");

		/*
		 * Headers - list is choosen based on HTTP method. Digest is not required for
		 * GET Method
		 */
		String getHeaders = "host date (request-target)" + " " + "v-c-merchant-id";
		// String getHeaders = "host (request-target)" + " " + "v-c-merchant-id";
		String postHeaders = "host date (request-target) digest v-c-merchant-id";

		if (httpMethod.equalsIgnoreCase("GET"))
			signatureHeaderValue.append(", headers=\"" + getHeaders + "\"");
		else if (httpMethod.equalsIgnoreCase("POST"))
			signatureHeaderValue.append(", headers=\"" + postHeaders + "\"");

		/* Get Value for paramter 'Signature' to be passed to Signature Header */
		String signatureValue = getSignatureParam(httpMethod,requestHost,resource,merchantId,merchantSecretKey);
		signatureHeaderValue.append(", signature=\"" + signatureValue + "\"");

		return signatureHeaderValue;
	}

	private String getSignatureParam(String httpMethod,String requestHost,String resource,String merchantId,String merchantSecretKey) throws Exception{

		/*
		 * This method returns value for paramter Signature which is then passed to
		 * Signature header paramter 'Signature' is calucated based on below key values
		 * and then signed with SECRET KEY - host: Sandbox (apitest.cybersource.com) or
		 * Production (api.cybersource.com) hostname date: "HTTP-date" format as defined
		 * by RFC7231. (request-target): Should be in format of httpMethod: path
		 * Example: "post /pts/v2/payments" Digest: Only needed for POST calls.
		 * digestString = BASE64( HMAC-SHA256 ( Payload )); Digest: “SHA-256=“ +
		 * digestString; v-c-merchant-id: set value to Cybersource Merchant ID This ID
		 * can be found on EBC portal
		 */

		StringBuilder signatureString = new StringBuilder();
		signatureString.append('\n');
		signatureString.append("host");
		signatureString.append(": ");
		signatureString.append(requestHost);
		signatureString.append('\n');
		signatureString.append("date");
		signatureString.append(": ");
		signatureString.append(gmtDateTime);
		signatureString.append('\n');
		signatureString.append("(request-target)");
		signatureString.append(": ");

		String getRequestTarget = "get " + resource;
		if (httpMethod.equalsIgnoreCase("GET"))
			signatureString.append(getRequestTarget);
		signatureString.append('\n');
		signatureString.append("v-c-merchant-id");
		signatureString.append(": ");
		signatureString.append(merchantId);
		signatureString.delete(0, 1);
		String signatureStr = signatureString.toString();
		
		/*
		 * Signature string generated from above parameters is Signed with SecretKey
		 * hased with SHA256 and base64 encoded. Secret Key is Base64 decoded before
		 * signing
		 */
		SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(merchantSecretKey), "HmacSHA256");
		Mac aKeyId = Mac.getInstance("HmacSHA256");
		aKeyId.init(secretKey);
		aKeyId.update(signatureStr.getBytes());
		byte[] aHeaders = aKeyId.doFinal();
		String base64EncodedSignature = Base64.getEncoder().encodeToString(aHeaders);
		

		return base64EncodedSignature;
	}

	
}
