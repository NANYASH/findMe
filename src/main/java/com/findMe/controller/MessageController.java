package com.findMe.controller;


import com.findMe.service.MessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MessageController {
    private static final Logger LOGGER = Logger.getLogger(MessageController.class);
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(path = "/send-message", method = RequestMethod.POST)
    public ResponseEntity sendMessage(){
        LOGGER.info("Message is sent.");
        return new ResponseEntity("Message is sent.", HttpStatus.OK);
    }

    @RequestMapping(path = "/edit-message", method = RequestMethod.POST)
    public ResponseEntity editMessage(){
        LOGGER.info("Message is edited.");
        return new ResponseEntity("Message is edited.", HttpStatus.OK);
    }

    @RequestMapping(path = "/delete-message", method = RequestMethod.POST)
    public ResponseEntity deleteMessage(){
        LOGGER.info("Message is deleted.");
        return new ResponseEntity("Message is deleted.", HttpStatus.OK);
    }
}
