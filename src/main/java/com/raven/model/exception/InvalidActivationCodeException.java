package com.raven.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Invalid activation code.")
public class InvalidActivationCodeException extends RuntimeException {

    public InvalidActivationCodeException(String message) {
        super(message);
    }

}
