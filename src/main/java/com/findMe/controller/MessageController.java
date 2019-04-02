package com.findMe.controller;


import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.Message;
import com.findMe.model.User;
import com.findMe.service.MessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.findMe.util.Util.convertId;
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
        messageService.updateMessage(message);
        LOGGER.info("Message is edited.");
        return new ResponseEntity("Message is updated.", HttpStatus.OK);
    }

    @RequestMapping(path = "/remove-selected-messages", method = RequestMethod.POST)
    public ResponseEntity removeSelected(HttpSession session, @ModelAttribute List<Message> messages) throws BadRequestException, InternalServerError, UnauthorizedException {
        User userLogged = validateLogIn(session);
        for (Message message : messages)
            message.setUserFrom(userLogged);
        messageService.updateSelectedMessages(messages);
        LOGGER.info("Messages are deleted.");
        return new ResponseEntity("Messages are deleted.", HttpStatus.OK);
    }

    @RequestMapping(path = "/remove-chat", method = RequestMethod.POST)
    public ResponseEntity removeAll(HttpSession session, @RequestParam String userId) throws BadRequestException, InternalServerError, UnauthorizedException {
        Long userLoggedId = validateLogIn(session).getId();
        messageService.updateAllMessages(userLoggedId, convertId(userId));
        LOGGER.info("Chat is deleted.");
        return new ResponseEntity("Chat is deleted.", HttpStatus.OK);
    }

}
