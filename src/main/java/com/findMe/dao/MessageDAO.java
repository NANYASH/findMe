package com.findMe.dao;


import com.findMe.exception.InternalServerError;
import com.findMe.model.Message;

public interface MessageDAO {
    Message save(Message message) throws InternalServerError;

    Message update(Message message) throws InternalServerError;

    void delete(Message message) throws InternalServerError;
}
