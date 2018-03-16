package com.raven.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Office does not exist.")
public class OfficeNotExistingException extends Exception {

    public OfficeNotExistingException() {
    }

    public OfficeNotExistingException(String message) {
        super(message);
    }

    public OfficeNotExistingException(String message, Throwable cause) {
        super(message, cause);
    }

    public OfficeNotExistingException(Throwable cause) {
        super(cause);
    }

}
