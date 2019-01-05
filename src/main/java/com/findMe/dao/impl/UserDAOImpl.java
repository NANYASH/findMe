package com.findMe.dao.impl;


import com.findMe.dao.UserDAO;
import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class UserDAOImpl extends GenericDAO<User> implements UserDAO {
    private static final String FIND_USER_BY_PHONE_AND_EMAIL = "SELECT * FROM user_table WHERE phone = ? OR email = ?";

    private static final String FIND_BY_RELATIONSHIP_STATUS = "SELECT  USER_TABLE.* FROM USER_TABLE JOIN RELATIONSHIP ON USER_FROM_ID = ? OR USER_TO_ID = ?" +
            " WHERE  USER_FROM_ID = USER_TABLE.ID OR USER_TO_ID = USER_TABLE.ID" +
            " GROUP BY (USER_TABLE.ID,STATUS)" +
            " HAVING   USER_TABLE.ID <> ? AND STATUS = ?";

    private static final String FIND_REQUESTED_FROM = "SELECT DISTINCT USER_TABLE.* FROM USER_TABLE JOIN RELATIONSHIP ON  USER_TABLE.ID = USER_TO_ID" +
            " WHERE USER_FROM_ID = ? AND STATUS = 'REQUESTED'";

    private static final String FIND_REQUESTED_TO = "SELECT DISTINCT USER_TABLE.* FROM USER_TABLE JOIN RELATIONSHIP ON  USER_TABLE.ID = USER_FROM_ID" +
            " WHERE USER_TO_ID = ? AND STATUS = 'REQUESTED'";


    @Override
    public User register(User user) throws InternalServerError, BadRequestException {
        if (findByPhoneAndEmail(user.getPhone(), user.getEmail()) != null)
            throw new BadRequestException("User with such phone/email already exists");
        return super.create(user);
    }

    @Override
    public void delete(Long id) throws InternalServerError {
        super.remove(id);
    }

    @Override
    public User findById(Long id) throws InternalServerError {
        return super.findById(id);
    }

    @Override
    public User findByPhoneAndEmail(String phone, String email) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(FIND_USER_BY_PHONE_AND_EMAIL, User.class);
            query.setParameter(1, phone);
            query.setParameter(2, email);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public List<User> findByRelationshipStatus(Long userId, RelationshipStatus status) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(FIND_BY_RELATIONSHIP_STATUS, User.class);
            query.setParameter(1, userId);
            query.setParameter(2, userId);
            query.setParameter(3, userId);
            query.setParameter(4, status.toString());
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public List<User> findRequestedFrom(Long userId) throws InternalServerError {
        return findRequested(userId, FIND_REQUESTED_FROM);
    }

    @Override
    public List<User> findRequestedTo(Long userId) throws InternalServerError {
        return findRequested(userId, FIND_REQUESTED_TO);
    }

    @Override
    Class<User> getEntityClass() {
        return User.class;
    }

    private List<User> findRequested(Long userId, String request) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(request, User.class);
            query.setParameter(1, userId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }
}
