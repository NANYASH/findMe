package com.findMe.dao.impl;


import com.findMe.dao.MessageDAO;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Transactional
@Repository
public class MessageDAOImpl extends GenericDAO<Message> implements MessageDAO {
    private static final String FIND_MESSAGES = "SELECT *" +
            " FROM MESSAGE" +
            " WHERE ((USER_FROM_ID = ? AND USER_TO_ID = ?) OR (USER_TO_ID = ? AND USER_FROM_ID = ?)) AND DATE_DELETED IS NULL" +
            " ORDER BY" +
            " CASE" +
            " WHEN DATE_READ IS NULL THEN 1" +
            " END ASC," +
            " DATE_SENT ASC";

    private static final String UPDATE_MESSAGES = "UPDATE Message m SET m.dateRead = :dateRead WHERE " +
            "m.id IN :idsList";


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
    public Message findMessageById(Long messageId) throws InternalServerError {
        return super.findById(messageId);
    }

    @Override
    public List<Message> findMessages(Long userFromId, Long userToId, Integer offset) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(FIND_MESSAGES, Message.class)
                    .setParameter(1, userFromId)
                    .setParameter(2, userToId)
                    .setParameter(3, userFromId)
                    .setParameter(4, userToId)
                    .setFirstResult(offset)
                    .setMaxResults(10);
            return query.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }


    @Override
    public void updateMessages(List<Long> messagesIds) throws InternalServerError {
        try {
            getEntityManager().createQuery(UPDATE_MESSAGES)
                    .setParameter("dateRead", LocalDate.now())
                    .setParameter("idsList", messagesIds)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    Class<Message> getEntityClass() {
        return Message.class;
    }
}
