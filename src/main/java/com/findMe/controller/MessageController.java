package com.findMe.controller;


import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.Message;
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

import java.time.LocalDate;

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
    public ResponseEntity sendMessage(HttpSession session, @ModelAttribute Message message) throws BadRequestException, InternalServerError, UnauthorizedException {
        message.setUserFrom(validateLogIn(session));
        messageService.addMessage(message);
        LOGGER.info("Message is sent.");
        return new ResponseEntity("Message is sent.", HttpStatus.OK);
    }

    @RequestMapping(path = "/edit-message", method = RequestMethod.POST)
    public ResponseEntity editMessage(HttpSession session, @ModelAttribute Message message) throws BadRequestException, InternalServerError, UnauthorizedException {
        message.setUserFrom(validateLogIn(session));
        message.setDateEdited(LocalDate.now());
        messageService.updateMessage(message);
        LOGGER.info("Message is edited.");
        return new ResponseEntity("Message is edited.", HttpStatus.OK);
    }

    @RequestMapping(path = "/delete-message", method = RequestMethod.POST)
    public ResponseEntity deleteMessage(HttpSession session, @ModelAttribute Message message) throws UnauthorizedException, BadRequestException, InternalServerError {
        message.setUserFrom(validateLogIn(session));
        message.setDateDeleted(LocalDate.now());
        messageService.updateMessage(message);
        LOGGER.info("Message is deleted.");
        return new ResponseEntity("Message is deleted.", HttpStatus.OK);
    }
}
