package com.findMe.service.impl;


import com.findMe.dao.MessageDAO;
import com.findMe.dao.RelationshipDAO;
import com.findMe.dao.UserDAO;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Message;
import com.findMe.model.Relationship;
import com.findMe.model.enums.RelationshipStatus;
import com.findMe.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private MessageDAO messageDAO;
    private RelationshipDAO relationshipDAO;

    @Autowired
    public MessageServiceImpl(MessageDAO messageDAO, RelationshipDAO relationshipDAO) {
        this.messageDAO = messageDAO;
        this.relationshipDAO = relationshipDAO;
    }

    @Override
    public Message addMessage(Message message) throws InternalServerError, BadRequestException {
        Relationship relationship = relationshipDAO.getRelationship(message.getUserFrom().getId(), message.getUserTo().getId());
        if (relationship.getRelationshipStatus().equals(RelationshipStatus.ACCEPTED))
            return messageDAO.save(message);
        throw new BadRequestException("Message cannot be send.");
    }

    @Override
    public Message updateMessage(Message message) throws InternalServerError, BadRequestException {
        validateUpdate(message);
        message.setDateEdited(LocalDate.now());
        return messageDAO.update(message);
    }

    @Override
    public void deleteMessage(Message message) throws InternalServerError, BadRequestException {
        validateUpdate(message);
        message.setDateDeleted(LocalDate.now());
        messageDAO.delete(message);
    }

    @Override
    public List<Message> findMessages(Long userFromId, Long userToId, Integer offset) throws InternalServerError {
        List<Long> messagesIdsToUpdate = new ArrayList<>();
        List<Message> messages = messageDAO.findMessages(userFromId, userToId, offset);
        for (Message message : messages) {
            if (message.getDateRead() == null) {
                if (message.getUserTo().getId().equals(userFromId)) {
                    message.setDateSent(LocalDate.now());
                    messagesIdsToUpdate.add(message.getId());
                }
            }
        }
        if (messagesIdsToUpdate.size() > 0)
            messageDAO.updateMessages(messagesIdsToUpdate);
        return messages;
    }

    private void validateUpdate(Message currentMessage) throws BadRequestException {
        if (currentMessage.getDateRead() != null) throw new BadRequestException("Message has been read.");
        if (currentMessage.getDateDeleted() != null) throw new BadRequestException("Message has been deleted.");
        if (currentMessage.getText().length() > 140) throw new BadRequestException("Message is too long");
    }

}
