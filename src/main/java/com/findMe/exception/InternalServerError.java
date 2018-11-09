package com.findMe.exception;


public class InternalServerError extends Exception{
    public InternalServerError(String cause) {
        super(cause);
    }

    public InternalServerError() {
        super();
    }
}
