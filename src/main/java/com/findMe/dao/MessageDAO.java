package com.findMe.dao;


import com.findMe.exception.InternalServerError;
import com.findMe.model.Message;

import java.util.List;

public interface MessageDAO {
    Message save(Message message) throws InternalServerError;

    Message update(Message message) throws InternalServerError;

    void delete(Message message) throws InternalServerError;

    Message findMessageById(Long messageId) throws InternalServerError;

    List<Message> findMessages(Long userFromId, Long userToId, Integer offset) throws InternalServerError;

    void updateMessagesDateRead(List<Long> messagesIds) throws InternalServerError;

    void updateMessagesDateDeleted(List<Message> messages) throws InternalServerError;

    void updateAllDateDeleted(Long userFromId, Long userToId) throws InternalServerError;
}
