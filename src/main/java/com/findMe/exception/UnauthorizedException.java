package com.findMe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="Unauthorized")
public class UnauthorizedException extends Exception{
    public UnauthorizedException(String message) {
        super(message);
    }
}
