package com.findMe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,reason = "INTERNAL_SERVER_ERROR")
public class InternalServerError extends Exception{
    public InternalServerError(String cause) {
        super(cause);
    }

    public InternalServerError() {
        super();
    }
}
