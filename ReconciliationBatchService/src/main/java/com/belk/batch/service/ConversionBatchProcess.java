package com.belk.batch.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.belk.batch.exception.DomBatchUpdateException;
import com.belk.batch.exception.OrderRetrievalException;
import com.belk.batch.model.Conversion;
import com.belk.batch.model.CustomerAcceptOrder;
import com.belk.batch.model.CustomerOrder;
import com.belk.batch.model.CustomerOrderDetail;
import com.belk.batch.model.CustomerRejectOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConversionBatchProcess {

	@Autowired
	private CreateOrUpdateService createOrUpdateService;
	
	@Value("${app.dom.orderdetails.authorization}")
	private String orderDetailsAuth;
	
	@Value("${app.dom.orderdetails.url}")
	private String orderDetailsUrl;
	
	
	@Value("${app.dom.createorupdate.url}")
	private String domCreateUpdateUrl;
	
	@Value("${app.dom.createorupdate.authorization}")
	private String domCreateUpdateAuth;
	
	@Value("${app.dom.companyid}")
	private String companyId;

	
	private final static Logger LOGGER = LoggerFactory.getLogger(ConversionBatchProcess.class);

	public void process(Conversion conversion, List<String> ordersUpdated) throws DomBatchUpdateException, URISyntaxException, JsonProcessingException,OrderRetrievalException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Conversion Details: {}", conversion.getMerchantReferenceNumber());
			
		}

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION,orderDetailsAuth);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(orderDetailsUrl)
				.queryParam("responseType", "FullCustomerOrder")
				.queryParam("customerOrderNumber", conversion.getMerchantReferenceNumber());

		HttpEntity entity = new HttpEntity(headers);
		
		ResponseEntity<CustomerOrderDetail> orderDetailsResponse = createOrUpdateService.getOrderDetails(builder.toUriString(), entity, conversion.getMerchantReferenceNumber());
		
		Optional<CustomerOrderDetail> customerOrder = Optional.ofNullable(orderDetailsResponse.getBody());
		if (customerOrder.get().getResponseStatus() && customerOrder.isPresent()
				&& null != customerOrder.get().getOrderNumber() && !customerOrder.get().getOrderCancelled()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Order Number: {}", customerOrder.get().getOrderNumber());
				LOGGER.debug("Order Hold Status {}", customerOrder.get().getOnHold());
			}

			if (customerOrder.get().getOnHold()) {
				CustomerOrder orderUpdate = transfomrtoDomRequest(conversion, customerOrder.get().getOrderType());
				ObjectMapper obJMapper = new ObjectMapper();
				String domRequestJson = obJMapper.writeValueAsString(orderUpdate);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("DOM Request to update Order: {}", domRequestJson);
				}
				URI uri = new URI(domCreateUpdateUrl);
				headers.set(HttpHeaders.AUTHORIZATION, domCreateUpdateAuth);

				HttpEntity<CustomerOrder> updateOrderRequest = new HttpEntity<CustomerOrder>(orderUpdate, headers);

				ResponseEntity<CustomerOrder> updateOrderResponse = createOrUpdateService.orderUpdate(uri, updateOrderRequest,
						orderUpdate.getOrderNumber());

				if (null != updateOrderResponse && !customerOrder.get().getOrderCancelled()) {
					ordersUpdated.add(customerOrder.get().getOrderNumber());
				}

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Order Update Status {}", updateOrderResponse);
				}

			}

		}

	}

	public CustomerOrder transfomrtoDomRequest(Conversion conversion, String orderType) {
		CustomerAcceptOrder customerAcceptOrder = null;
		CustomerRejectOrder customerRejectOrder = null;
		CustomerOrder customerOrder = null;

		if ("Accept".equalsIgnoreCase(conversion.getNewDecision())) {
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
		customerOrder.setOrderNumber(conversion.getMerchantReferenceNumber());
		customerOrder.setOrderType(orderType);

		return customerOrder;
	}

}
