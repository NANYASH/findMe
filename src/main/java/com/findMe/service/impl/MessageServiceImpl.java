package com.findMe.service.impl;


import com.findMe.dao.MessageDAO;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Message;
import com.findMe.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{
    private MessageDAO messageDAO;

    @Autowired
    public MessageServiceImpl(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @Override
    public Message addMessage(Message message) throws InternalServerError {
        return messageDAO.save(message);
    }

    @Override
    public Message updateMessage(Message message) throws InternalServerError {
        return messageDAO.update(message);
    }

    @Override
    public void deleteMessage(Message message) throws InternalServerError {
         messageDAO.delete(message);
    }
}
