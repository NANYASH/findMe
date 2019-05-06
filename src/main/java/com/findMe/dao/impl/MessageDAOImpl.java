package com.findMe.dao.impl;


import com.findMe.dao.MessageDAO;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
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

    private static final String UPDATE_MESSAGES_DATE_READ = "UPDATE Message m SET m.dateRead = :paramDate WHERE " +
            "m.id IN :idsList";

    private static final String UPDATE_MESSAGES_DATE_DELETED = "UPDATE Message m SET m.dateDeleted = :paramDate WHERE " +
            "m IN :messagesList";

    private static final String UPDATE_ALL_DATE_DELETED = "UPDATE MESSAGE SET DATE_DELETED = ? " +
            "WHERE ((USER_FROM_ID = ? AND USER_TO_ID = ?) OR (USER_TO_ID = ? AND USER_FROM_ID = ?)) AND DATE_DELETED IS NULL";

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
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }


    @Override
    public void updateMessagesDateRead(List<Long> messagesIds) throws InternalServerError {
        try {
            getEntityManager().createQuery(UPDATE_MESSAGES_DATE_READ)
                    .setParameter("paramDate", LocalDate.now())
                    .setParameter("idsList", messagesIds)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public void updateMessagesDateDeleted(List<Message> messages) throws InternalServerError {
        try {
            getEntityManager().createQuery(UPDATE_MESSAGES_DATE_DELETED)
                    .setParameter("paramDate", LocalDate.now())
                    .setParameter("messagesList", messages)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public void updateAllDateDeleted(Long userFromId, Long userToId) throws InternalServerError {
        try {
            getEntityManager().createNativeQuery(UPDATE_ALL_DATE_DELETED)
                    .setParameter(1, LocalDate.now())
                    .setParameter(2, userFromId)
                    .setParameter(3, userToId)
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
