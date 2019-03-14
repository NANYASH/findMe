package com.findMe.service;


import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Message;
import com.findMe.model.viewData.MessageParametersData;

import java.util.List;

public interface MessageService {
    Message addMessage(MessageParametersData messageParametersData) throws InternalServerError, BadRequestException;

    Message updateMessage(MessageParametersData messageParametersData) throws InternalServerError, BadRequestException;

    void deleteMessage(MessageParametersData messageParametersData) throws InternalServerError, BadRequestException;

    List<Message> findMessages(Long userFromId, Long userToId, Integer offset) throws InternalServerError;

}
