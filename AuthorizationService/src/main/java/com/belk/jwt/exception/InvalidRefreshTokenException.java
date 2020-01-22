package com.belk.jwt.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidRefreshTokenException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -125647088462731420L;

	public InvalidRefreshTokenException() {
        super("Invalid refresh token");
    }

}

