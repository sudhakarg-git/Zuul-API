package com.belk.fraudValidation.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.belk.fraudValidation.exception.OrderUpdateException;
import com.belk.fraudValidation.model.CaseManagementOrderStatus;
import com.belk.fraudValidation.model.CustomerOrder;
import com.belk.fraudValidation.model.CustomerOrderDetail;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@RestController
public class FraudValidationService {

	private final static Logger LOGGER = LoggerFactory.getLogger(FraudValidationService.class);

	@Autowired
	private CreateOrUpdateService createOrUpdateService;

	@Value("${app.dom.orderdetails.authorization}")
	private String orderDetailsAuth;

	@Value("${app.dom.orderdetails.url}")
	private String orderDetailsUrl;

	@PostMapping(path = "/orders", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public void updateOrderStatus(@RequestParam Map<String, String> csResponse)
			throws URISyntaxException, JsonParseException, JsonMappingException, IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Incoming Request: {}", csResponse);
		}
		XmlMapper xmlMapper = new XmlMapper();
		CaseManagementOrderStatus cyberSourceRequest = xmlMapper.readValue(csResponse.get("content"),
				CaseManagementOrderStatus.class);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, orderDetailsAuth);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(orderDetailsUrl)
				.queryParam("responseType", "FullCustomerOrder")
				.queryParam("customerOrderNumber", cyberSourceRequest.getUpdate().getMerchantReferenceNumber());

		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity<CustomerOrderDetail> orderDetailsResponse = createOrUpdateService.retrieveCustmerOrderDetails(
				builder.toUriString(), entity, cyberSourceRequest.getUpdate().getMerchantReferenceNumber());

		Optional<CustomerOrderDetail> customerOrder = Optional.ofNullable(orderDetailsResponse.getBody());

		if (customerOrder.get().getResponseStatus() && customerOrder.isPresent()
				&& null != customerOrder.get().getOrderNumber() && !customerOrder.get().getOrderCancelled()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Order Number: {}", customerOrder.get().getOrderNumber());
				LOGGER.debug("Order Hold Status {}", customerOrder.get().getOnHold());
			}

			if (customerOrder.get().getOnHold()) {
				ResponseEntity<CustomerOrder> orderUpdate = null;
				try {
					orderUpdate = createOrUpdateService.orderUpdate(cyberSourceRequest,
							customerOrder.get().getOrderType());
				} catch (OrderUpdateException ex) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Error in Controller", ex.getMessage());
					}
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("DOM Response after Order Update: {}", orderUpdate);
				}
			}
		}

	}

}
