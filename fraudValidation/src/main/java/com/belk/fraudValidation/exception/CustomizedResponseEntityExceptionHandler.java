package com.belk.fraudValidation.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		DOMExceptionResponse domExceptionResponse = new DOMExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Exception Occured : {}", domExceptionResponse.getMessage());
		}
		return new ResponseEntity(domExceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	@ExceptionHandler(OrderUpdateException.class)
	public final ResponseEntity<DOMExceptionResponse> handleAllExceptions(OrderUpdateException ex, WebRequest request)  {
		DOMExceptionResponse domExceptionResponse = new DOMExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Exception Occured : {}", domExceptionResponse.getMessage());
		}
		return new ResponseEntity<DOMExceptionResponse>(domExceptionResponse,HttpStatus.NOT_MODIFIED);
	}
	
	@ExceptionHandler(DomOrderRetrievalException.class)
	public final ResponseEntity<DOMExceptionResponse> handleAllExceptions(DomOrderRetrievalException ex, WebRequest request)  {
		DOMExceptionResponse domExceptionResponse = new DOMExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Exception Occured : {}", domExceptionResponse.getMessage());
		}
		return new ResponseEntity<DOMExceptionResponse>(domExceptionResponse,HttpStatus.NOT_FOUND);
	}

}
