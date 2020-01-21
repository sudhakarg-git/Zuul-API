package com.belk.fraudValidation.service;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.belk.fraudValidation.exception.OrderUpdateException;
import com.belk.fraudValidation.mail.BatchMailSender;
import com.belk.fraudValidation.model.CaseManagementOrderStatus;
import com.belk.fraudValidation.model.CustomerAcceptOrder;
import com.belk.fraudValidation.model.CustomerOrder;
import com.belk.fraudValidation.model.CustomerOrderDetail;
import com.belk.fraudValidation.model.CustomerRejectOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CreateOrUpdateService {

	private final static Logger LOGGER = LoggerFactory.getLogger(CreateOrUpdateService.class);

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RetryTemplate retryTemplate;

	@Value("${app.dom.orderdetails.authorization}")
	private String orderDetailsAuth;

	@Value("${app.dom.orderdetails.url}")
	private String orderDetailsUrl;

	@Value("${app.dom.url}")
	private String domCreateUpdateUrl;

	@Value("${app.dom.authorization}")
	private String domCreateUpdateAuth;

	@Value("${app.dom.companyid}")
	private String companyId;

	@Autowired
	private BatchMailSender mailSender;
	
	
	
	public ResponseEntity<CustomerOrderDetail> retrieveCustmerOrderDetails(String url, HttpEntity entity,String orderNumber)
			throws URISyntaxException, OrderUpdateException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside retrieveCustmerOrderDetails of CreateOrUpdateService : {}", orderNumber);
		}
		RetryCallback<ResponseEntity<CustomerOrderDetail>, OrderUpdateException> retryCallback = new RetryCallback<ResponseEntity<CustomerOrderDetail>, OrderUpdateException>() {
			ResponseEntity<CustomerOrderDetail> orderRetrievalResponse = null;
			@Override
			public ResponseEntity<CustomerOrderDetail> doWithRetry(RetryContext context) throws OrderUpdateException {
				try {
					orderRetrievalResponse = restTemplate.exchange(url, HttpMethod.GET, entity, CustomerOrderDetail.class);
					Optional<CustomerOrderDetail> customerOrder = Optional.ofNullable(orderRetrievalResponse.getBody());

					if (!customerOrder.get().getResponseStatus() && (null == customerOrder.get().getOrderNumber())) {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Order Not Found Retrying to retrieve Order from DOM. ");  
						}
						throw new OrderUpdateException("Order Not Found");
					}
                    
				} catch (RestClientException ex) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Error Message : {}", ex.getMessage());  
					}
					String mailBody = new StringBuilder()
							          .append("Dear Team,\n")
							          .append("Unable to send review decision from cyber source for order number: ")
							          .append(orderNumber)
							          .append(" due to error occurred to invoke DOM OrderDetails Service.\n")
							          .toString();
					mailSender.sendEmail(mailBody);
					throw new OrderUpdateException(mailBody);
				}
				return orderRetrievalResponse;
			}
		};
		
		return retryTemplate.execute(retryCallback);

	}


	public ResponseEntity<CustomerOrder> orderUpdate(CaseManagementOrderStatus cyberSourceRequest, String orderType)
			throws URISyntaxException, OrderUpdateException, JsonProcessingException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside CreateOrUpdateService : {}",
					cyberSourceRequest.getUpdate().getMerchantReferenceNumber());
		}

		CustomerOrder customerOrder = transfomrtoDomRequest(cyberSourceRequest, orderType);
		ObjectMapper obJMapper = new ObjectMapper();
		String domRequestJson = obJMapper.writeValueAsString(customerOrder);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("DOM Request to update Order: {}", domRequestJson);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, domCreateUpdateAuth);
		headers.setContentType(MediaType.APPLICATION_JSON); 

		HttpEntity<CustomerOrder> request = new HttpEntity<CustomerOrder>(customerOrder, headers);

		RetryCallback<ResponseEntity<CustomerOrder>, OrderUpdateException> retryCallback = new RetryCallback<ResponseEntity<CustomerOrder>, OrderUpdateException>() {
			ResponseEntity<CustomerOrder> response = null;
			@Override
			public ResponseEntity<CustomerOrder> doWithRetry(RetryContext context) throws OrderUpdateException {
				try {
					response = restTemplate.exchange(domCreateUpdateUrl, HttpMethod.POST, request, CustomerOrder.class);

				} catch (RestClientException ex) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Error Message : {}", ex.getMessage());  
					}
					String mailBody = new StringBuilder().append("Dear Team,\n")
							.append("Unable to send review decision from cyber source for order number: ")
							.append(customerOrder.getOrderNumber())
							.append(" due to error occurred to invoke DOM Service.\n").toString();

					mailSender.sendEmail(mailBody);
					throw new OrderUpdateException(ex.getMessage());
				}
				return response;
			}
		};

		return retryTemplate.execute(retryCallback);

	}

	public CustomerOrder transfomrtoDomRequest(CaseManagementOrderStatus orderStatus, String orderType) {
		CustomerAcceptOrder customerAcceptOrder = null;
		CustomerRejectOrder customerRejectOrder = null;
		CustomerOrder customerOrder = null;

		if ("Accept".equalsIgnoreCase(orderStatus.getUpdate().getNewDecision())) {
			customerAcceptOrder = new CustomerAcceptOrder();
			customerAcceptOrder.setOnHold(false);
			customerAcceptOrder.setReasonCode("");
			customerOrder = customerAcceptOrder;

		} else {
			customerRejectOrder = new CustomerRejectOrder();
			customerRejectOrder.setOrderCancelled(true);
			customerOrder = customerRejectOrder;

		}

		customerOrder.setCompanyId(companyId);
		customerOrder.setOrderConfirmed(true);
		customerOrder.setOrderNumber(orderStatus.getUpdate().getMerchantReferenceNumber());
		customerOrder.setOrderType(orderType);

		return customerOrder;
	}

	@Bean
	public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
				.build();

		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
		return restTemplate;
	}

}
