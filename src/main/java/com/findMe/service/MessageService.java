package com.findMe.service;


import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Message;

import java.util.List;

public interface MessageService {
    Message addMessage(Message message) throws InternalServerError, BadRequestException;

    Message updateMessage(Message message) throws InternalServerError, BadRequestException;

    void deleteMessage(Message message) throws InternalServerError, BadRequestException;

    List<Message> findMessages(Long userFromId, Long userToId, Integer offset) throws InternalServerError;

}
