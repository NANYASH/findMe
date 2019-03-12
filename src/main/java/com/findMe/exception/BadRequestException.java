package com.findMe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "BadRequestException")
public class BadRequestException extends Exception{
    public BadRequestException(String cause) {
        super(cause);
    }
}
