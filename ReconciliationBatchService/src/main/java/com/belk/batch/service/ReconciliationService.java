package com.belk.batch.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.belk.batch.exception.CyberSourceException;
import com.belk.batch.mail.BatchMailSender;
import com.belk.batch.model.Conversion;
import com.belk.batch.model.ReconciliationReport;
import com.cybersource.authsdk.core.MerchantConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import Api.ConversionDetailsApi;
import Invokers.ApiClient;
import Invokers.ApiException;

@Service
public class ReconciliationService {

	@Autowired
	private ConversionBatchProcess conversionBatchProcess;

	@Value("${host}")
	private String host;

	@Value("${merchantID}")
	private String merchantID;

	@Value("${merchantKeyId}")
	private String merchantKeyId;

	@Value("${merchantSecretKey}")
	private String merchantSecretKey;

	@Value("${resource}")
	private String resourcePath;

	@Value("${app.cs.url}")
	private String csUrl;

	@Value("${authenticationType}")
	private String authenticationType;
	
	@Value("${merchantSecretKey}")
	private String merchantsecretKey;
	
	@Value("${runEnvironment}")
	private String runEnvironment;

	@Autowired
	private BatchMailSender mailSender;

	private final static Logger LOGGER = LoggerFactory.getLogger(ReconciliationService.class);

	public ReconciliationReport reconciliationPorcess(DateTime startTime, DateTime endTime, String organizationId)
			throws Exception {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside Reconciliation Service: {} {}",startTime,endTime);
		}
		ReconciliationReport concilicationReport = null;
		try {

			/* Read Merchant details. */
			MerchantConfig merchantConfig = new MerchantConfig();
			merchantConfig.setAuthenticationType(authenticationType);
			merchantConfig.setMerchantID(merchantID);
			merchantConfig.setMerchantKeyId(merchantKeyId);
			merchantConfig.setMerchantSecretKey(merchantsecretKey);
			merchantConfig.setRunEnvironment(runEnvironment);
			merchantConfig.setRequestHost(host);

			ApiClient apiClient = new ApiClient();
			apiClient.merchantConfig = merchantConfig;

			ConversionDetailsApi conversionDetailsApi = new ConversionDetailsApi(apiClient);
			conversionDetailsApi.getConversionDetail(startTime, endTime, organizationId);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("ResponseCode : {}", ApiClient.responseCode);
				LOGGER.debug("Status : {}", ApiClient.status);

			}
			ObjectMapper obJMapper = new ObjectMapper();
			concilicationReport = obJMapper.readValue(apiClient.respBody, ReconciliationReport.class);
			List<String> updatedOrders = null;
			updatedOrders = new ArrayList<String>();

			for (Conversion conversion : concilicationReport.getConversionDetails()) {
				conversionBatchProcess.process(conversion, updatedOrders);
			}

			if (null != updatedOrders && updatedOrders.size() > 0) {

				StringBuilder ordersBuilder = new StringBuilder();
				for (String orderNumber : updatedOrders) {
					ordersBuilder.append(orderNumber);
					ordersBuilder.append('\n');
				}
				String mailBody = new StringBuilder().append("Dear Team,\n")
						.append("Following Orders got updated during Reconciliation Process : \n").append('\n')
						.append(ordersBuilder.toString()).toString();
				mailSender.sendEmail(mailBody);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Orders Processed during Reconciliation Batch Process: {}", mailBody);
				}
			}

		} catch (ApiException ex) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(ex.getMessage());
			}
			String mailBody = null;
			if("Not Found".equalsIgnoreCase(ex.getMessage()))
			{
				String reSourceNotFound = new StringBuilder().append("Dear Team,\n")
						.append("Unable to retrieve Conversion Daily Report from CyberSource")
						.append(" for the Given Inputs.").toString();
				if(LOGGER.isDebugEnabled()) {
					LOGGER.debug(reSourceNotFound);
				}
				
			}else {
			 mailBody = new StringBuilder().append("Dear Team,\n")
					.append("Unable to retrieve Conversion Daily Report from CyberSource")
					.append(" due to error occurred to invoke CyberSource Conversion Details Report Service. Error is :")
					.append(ex.getMessage()).toString();
			}
			mailSender.sendEmail(mailBody);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Error Occured during Reconciliation Batch Process: {}", mailBody);
			}

			throw new CyberSourceException(ex.getMessage());
		}

		return concilicationReport;
	}

}
