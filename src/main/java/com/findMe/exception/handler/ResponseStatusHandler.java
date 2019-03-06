package com.findMe.exception.handler;

import com.findMe.controller.UserController;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResponseStatusHandler {
    private static final Logger LOGGER = Logger.getLogger(ResponseStatusHandler.class);

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity badRequestHandler(HttpServletRequest request, Exception e){
        LOGGER.error(request.getMethod()+" "+e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity notFoundHandler(HttpServletRequest request, Exception e){
        LOGGER.error(request.getMethod()+" "+e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InternalServerError.class)
    public ResponseEntity internalServerErrorHandler(HttpServletRequest request, Exception e){
        LOGGER.error(request.getMethod()+" "+e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
