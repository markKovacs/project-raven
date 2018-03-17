package com.raven.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Invalid activation code.")
public class InvalidActivationCodeException extends RuntimeException {

    public InvalidActivationCodeException(String message) {
        super(message);
    }

    public InvalidActivationCodeException() {
    }

    public InvalidActivationCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidActivationCodeException(Throwable cause) {
        super(cause);
    }

}
