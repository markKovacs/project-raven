package com.raven.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Email is already registered.")
public class EmailExistsException extends Exception{

    public EmailExistsException() {
    }

    public EmailExistsException(String message) {
        super(message);
    }

    public EmailExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailExistsException(Throwable cause) {
        super(cause);
    }

}
