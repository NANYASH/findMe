package com.findMe.exception;


public class BadRequestException extends Exception{
    public BadRequestException(String cause) {
        super(cause);
    }
}
