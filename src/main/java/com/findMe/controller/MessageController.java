package com.findMe.controller;


import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.viewData.MessageParametersData;
import com.findMe.service.MessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

import static com.findMe.util.Util.validateLogIn;


@Controller
public class MessageController {
    private static final Logger LOGGER = Logger.getLogger(MessageController.class);
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(path = "/send-message", method = RequestMethod.POST)
    public ResponseEntity sendMessage(HttpSession session, @ModelAttribute MessageParametersData messageParametersData) throws BadRequestException, InternalServerError, UnauthorizedException {
        messageParametersData.setUserFromId(validateLogIn(session).getId());
        messageService.addMessage(messageParametersData);
        LOGGER.info("Message is sent.");
        return new ResponseEntity("Message is sent.", HttpStatus.OK);
    }

    @RequestMapping(path = "/edit-message", method = RequestMethod.POST)
    public ResponseEntity editMessage(HttpSession session, @ModelAttribute MessageParametersData messageParametersData) throws BadRequestException, InternalServerError, UnauthorizedException {
        messageParametersData.setUserFromId(validateLogIn(session).getId());
        messageService.updateMessage(messageParametersData);
        LOGGER.info("Message is edited.");
        return new ResponseEntity("Message is edited.", HttpStatus.OK);
    }

    @RequestMapping(path = "/delete-message", method = RequestMethod.POST)
    public ResponseEntity deleteMessage(HttpSession session, @ModelAttribute MessageParametersData messageParametersData) throws UnauthorizedException, BadRequestException, InternalServerError {
        messageParametersData.setUserFromId(validateLogIn(session).getId());
        messageService.deleteMessage(messageParametersData);
        LOGGER.info("Message is deleted.");
        return new ResponseEntity("Message is deleted.", HttpStatus.OK);
    }
}
