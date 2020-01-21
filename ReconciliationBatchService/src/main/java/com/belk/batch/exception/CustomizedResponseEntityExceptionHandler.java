package com.belk.batch.exception;

import java.util.Date;

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
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		BatchExceptionResponse batchExceptionResponse = new BatchExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity(batchExceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(BatchUpdateException.class)
	public final ResponseEntity<BatchExceptionResponse> handleAllExceptions(BatchUpdateException ex, WebRequest request)  {
		BatchExceptionResponse batchExceptionResponse = new BatchExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<BatchExceptionResponse>(batchExceptionResponse,HttpStatus.NOT_MODIFIED);
	}
	
	@ExceptionHandler(CyberSourceException.class)
	public final ResponseEntity<BatchExceptionResponse> handleAllExceptions(CyberSourceException ex, WebRequest request)  {
		BatchExceptionResponse batchExceptionResponse = new BatchExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<BatchExceptionResponse>(batchExceptionResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(OrderRetrievalException.class)
	public final ResponseEntity<BatchExceptionResponse> handleAllExceptions(OrderRetrievalException ex, WebRequest request)  {
		BatchExceptionResponse batchExceptionResponse = new BatchExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<BatchExceptionResponse>(batchExceptionResponse,HttpStatus.NOT_FOUND);
	}

}
