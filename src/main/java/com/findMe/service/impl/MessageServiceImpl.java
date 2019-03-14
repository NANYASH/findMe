package com.findMe.service.impl;


import com.findMe.dao.MessageDAO;
import com.findMe.dao.RelationshipDAO;
import com.findMe.dao.UserDAO;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Message;
import com.findMe.model.Relationship;
import com.findMe.model.enums.RelationshipStatus;
import com.findMe.model.viewData.MessageParametersData;
import com.findMe.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private MessageDAO messageDAO;
    private RelationshipDAO relationshipDAO;
    private UserDAO userDAO;

    @Autowired
    public MessageServiceImpl(MessageDAO messageDAO, RelationshipDAO relationshipDAO, UserDAO userDAO) {
        this.messageDAO = messageDAO;
        this.relationshipDAO = relationshipDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Message addMessage(MessageParametersData messageParametersData) throws InternalServerError, BadRequestException {
        Relationship relationship = relationshipDAO.getRelationship(messageParametersData.getUserFromId(), messageParametersData.getUserToId());
        if (relationship.getRelationshipStatus().equals(RelationshipStatus.ACCEPTED))
            return messageDAO.save(buildMessage(messageParametersData));
        throw new BadRequestException("Message cannot be send.");
    }

    @Override
    public Message updateMessage(MessageParametersData messageParametersData) throws InternalServerError, BadRequestException {
        Message currentMessage = messageDAO.findMessageById(messageParametersData.getId());
        validateUpdate(messageParametersData, currentMessage);
        currentMessage.setText(messageParametersData.getText());
        currentMessage.setDateEdited(LocalDate.now());
        return messageDAO.update(currentMessage);
    }

    @Override
    public void deleteMessage(MessageParametersData messageParametersData) throws InternalServerError, BadRequestException {
        Message currentMessage = messageDAO.findMessageById(messageParametersData.getId());
        validateUpdate(messageParametersData, currentMessage);
        currentMessage.setDateDeleted(LocalDate.now());
        messageDAO.delete(currentMessage);
    }

    @Override
    public List<Message> findMessages(Long userFromId, Long userToId, Integer offset) throws InternalServerError {
        return messageDAO.findMessages(userFromId, userToId, offset);
    }

    private void validateUpdate(MessageParametersData messageParametersData, Message currentMessage) throws BadRequestException {
        if (currentMessage == null) throw new BadRequestException("No message with such id");
        if (currentMessage.getDateRead() != null) throw new BadRequestException("Message has already been read.");
        if (currentMessage.getDateDeleted() != null) throw new BadRequestException("Message has been deleted.");
        if (!messageParametersData.getUserFromId().equals(currentMessage.getUserFrom().getId()))
            throw new BadRequestException("Message hasn't been sent by this user.");
        if (!messageParametersData.getUserToId().equals(currentMessage.getUserTo().getId()))
            throw new BadRequestException("Message hasn't been sent to this user.");
    }

    private Message buildMessage(MessageParametersData messageParametersData) throws InternalServerError {
        Message message = new Message();
        message.setText(messageParametersData.getText());
        message.setUserFrom(userDAO.findById(messageParametersData.getUserFromId()));
        message.setUserTo(userDAO.findById(messageParametersData.getUserToId()));
        message.setDateSent(LocalDate.now());
        return message;
    }
}
