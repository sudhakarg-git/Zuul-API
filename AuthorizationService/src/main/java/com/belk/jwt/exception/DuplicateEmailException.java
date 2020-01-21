package com.belk.jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEmailException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5033253034177275975L;

	public DuplicateEmailException() {
        super("Email already exists");
    }
}
