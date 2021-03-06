package com.findMe.dao.impl;


import com.findMe.dao.UserDAO;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Transactional
@Repository
public class UserDAOImpl extends GenericDAO<User> implements UserDAO {
    private static final String FIND_USER_BY_PHONE_AND_EMAIL = "SELECT * FROM user_table WHERE phone = ? OR email = ?";

    @Override
    public User add(User user) throws InternalServerError, BadRequestException {
        if (getByPhoneOrEmail(user.getPhone(), user.getEmail()) != null)
            throw new BadRequestException("User with such phone/email already exists");
        return super.create(user);
    }

    @Override
    public void delete(Long id) throws InternalServerError {
        super.delete(id);
    }

    @Override
    public User getById(Long id) throws InternalServerError {
        return super.getById(id);
    }

    @Override
    public User getByPhoneOrEmail(String phone, String email) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(FIND_USER_BY_PHONE_AND_EMAIL, User.class)
                    .setParameter(1, phone)
                    .setParameter(2, email);
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
    Class<User> getEntityClass() {
        return User.class;
    }
}
