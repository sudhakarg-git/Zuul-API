package com.belk.batch.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.belk.batch.exception.DomBatchUpdateException;
import com.belk.batch.exception.OrderRetrievalException;
import com.belk.batch.mail.BatchMailSender;
import com.belk.batch.model.CustomerOrder;
import com.belk.batch.model.CustomerOrderDetail;
	
@Service
public class CreateOrUpdateService {

	private final static Logger LOGGER = LoggerFactory.getLogger(CreateOrUpdateService.class);

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RetryTemplate retryTemplate;
	
	@Autowired
	private BatchMailSender mailSender;
	
	
	public ResponseEntity<CustomerOrderDetail> getOrderDetails(String url, HttpEntity entity,String orderNumber)
			throws URISyntaxException, OrderRetrievalException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside getOrderDetails of CreateOrUpdateService : {}", orderNumber);
		}
		
		RetryCallback<ResponseEntity<CustomerOrderDetail>, OrderRetrievalException> retryCallback = new RetryCallback<ResponseEntity<CustomerOrderDetail>, OrderRetrievalException>() {
			ResponseEntity<CustomerOrderDetail> orderRetrievalResponse = null;
			@Override
			public ResponseEntity<CustomerOrderDetail> doWithRetry(RetryContext context) throws OrderRetrievalException {
				try {
					orderRetrievalResponse = restTemplate.exchange(url, HttpMethod.GET, entity, CustomerOrderDetail.class);
                    
				} catch (RestClientException ex) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Error Message : {}", ex.getMessage());  
					}
					String mailBody = new StringBuilder()
							          .append("Dear Team,\n")
							          .append("Unable to send review decision from cyber source for order number: ")
							          .append(orderNumber)
							          .append("due to error occurred to invoke DOM OrderDetails Service.\n")
							          .toString();
					mailSender.sendEmail(mailBody);
					throw new OrderRetrievalException(mailBody);
				}
				return orderRetrievalResponse;
			}
		};
		
		return retryTemplate.execute(retryCallback);

	}
	
	public ResponseEntity<CustomerOrder> orderUpdate(URI uri, HttpEntity<CustomerOrder> request,String orderNumber)
			throws URISyntaxException, DomBatchUpdateException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside CreateOrUpdateService : {}", request.getBody().getOrderNumber());
		}
		
		RetryCallback<ResponseEntity<CustomerOrder>, DomBatchUpdateException> retryCallback = new RetryCallback<ResponseEntity<CustomerOrder>, DomBatchUpdateException>() {
			ResponseEntity<CustomerOrder> response = null;
			@Override
			public ResponseEntity<CustomerOrder> doWithRetry(RetryContext context) throws DomBatchUpdateException {
				try {
					response = restTemplate.exchange(uri, HttpMethod.POST, request, CustomerOrder.class);
                    
				} catch (RestClientException ex) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Error Message : {}", ex.getMessage());  
					}
					String mailBody = new StringBuilder()
							          .append("Dear Team,\n")
							          .append("Unable to send review decision from cyber source for order number: ")
							          .append(orderNumber)
							          .append(" due to error occurred to invoke DOM Service.\n" )
							          .toString();
					mailSender.sendEmail(mailBody);
					throw new DomBatchUpdateException(mailBody);
				}
				return response;
			}
		};
		
		return retryTemplate.execute(retryCallback);

	}
	

	

}
