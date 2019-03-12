package com.findMe.dao.impl;


import com.findMe.dao.MessageDAO;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Message;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public class MessageDAOImpl extends GenericDAO<Message> implements MessageDAO {
    @Override
    public Message save(Message message) throws InternalServerError {
        return super.save(message);
    }

    @Override
    public Message update(Message message) throws InternalServerError {
        return super.update(message);
    }

    @Override
    public void delete(Message message) throws InternalServerError {
        super.remove(message.getId());
    }

    @Override
    Class<Message> getEntityClass() {
        return Message.class;
    }
}
