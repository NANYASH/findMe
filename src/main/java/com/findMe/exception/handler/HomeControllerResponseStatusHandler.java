package com.findMe.exception.handler;

import com.findMe.controller.HomeController;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(assignableTypes = HomeController.class)
public class HomeControllerResponseStatusHandler {
    private static final Logger LOGGER = Logger.getLogger(HomeControllerResponseStatusHandler.class);

    @ExceptionHandler(value = BadRequestException.class)
    public ModelAndView badRequestHandler(HttpServletRequest request, Exception e){
        LOGGER.error(request.getMethod()+" "+e.getMessage());
        return new ModelAndView("error4",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ModelAndView notFoundHandler(HttpServletRequest request, Exception e){
        LOGGER.error(request.getMethod()+" "+e.getMessage());
        return new ModelAndView("error404", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InternalServerError.class)
    public ModelAndView internalServerErrorHandler(HttpServletRequest request, Exception e){
        LOGGER.error(request.getMethod()+" "+e.getMessage());
        return new ModelAndView("error500", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
