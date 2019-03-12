package com.findMe.service;


import com.findMe.exception.InternalServerError;
import com.findMe.model.Message;

public interface MessageService {
    Message addMessage(Message message) throws InternalServerError;
    Message updateMessage(Message message) throws InternalServerError;
    void deleteMessage(Message message) throws InternalServerError;
}
