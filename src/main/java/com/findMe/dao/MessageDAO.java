package com.findMe.dao;


import com.findMe.exception.InternalServerError;
import com.findMe.model.Message;

import java.util.List;

public interface MessageDAO {
    Message create(Message message) throws InternalServerError;

    Message update(Message message) throws InternalServerError;

    void delete(Message message) throws InternalServerError;

    Message getById(Long messageId) throws InternalServerError;

    List<Message> getAll(Long userFromId, Long userToId, Integer offset) throws InternalServerError;

    void updateDateRead(List<Long> messagesIds) throws InternalServerError;

    void updateDateDeleted(List<Message> messages) throws InternalServerError;

    void updateAllDateDeleted(Long userFromId, Long userToId) throws InternalServerError;
}
