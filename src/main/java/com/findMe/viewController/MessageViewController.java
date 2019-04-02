package com.findMe.viewController;


import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.NotFoundException;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.Message;
import com.findMe.model.User;
import com.findMe.service.MessageService;
import com.findMe.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.findMe.util.Util.convertId;
import static com.findMe.util.Util.validateLogIn;

@Controller
public class MessageViewController {
    private static final Logger LOGGER = Logger.getLogger(MessageViewController.class);
    private MessageService messageService;

    @Autowired
    public MessageViewController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(path = "/chat/{userId}", method = RequestMethod.GET)
    public String getMessages(HttpSession session, Model model, @PathVariable String userId, @RequestParam(required = false) String currentOffset) throws BadRequestException, InternalServerError, UnauthorizedException, NotFoundException {
        Integer offset = 0;
        List<Message> messages;
        User userSession = validateLogIn(session);
        Long userToId = convertId(userId);

        if (currentOffset == null) {
            messages = messageService.findMessages(userSession.getId(),userToId, offset);
            offset = 20;
        } else {
            offset = Integer.valueOf(currentOffset);
            messages = messageService.findMessages(userSession.getId(),userToId, offset);
            offset += 20;
        }
        model.addAttribute("messages", messages);
        model.addAttribute("offset", offset);
        LOGGER.info("Chat is opened.");
        return "message";
    }
}
