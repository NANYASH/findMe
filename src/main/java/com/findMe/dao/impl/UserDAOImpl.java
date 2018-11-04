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
    private static final String FIND_USER_BY_PHONE = "SELECT * FROM user_table WHERE phone = ?";

    @Override
    public void delete(Long id) throws InternalServerError {
        super.remove(id);
    }

    @Override
    public User findById(Long id) throws InternalServerError {
        return super.findById(id);
    }

    @Override
    public User findByPhone(String phone){
        User user;
            Query query = getEntityManager().createNativeQuery(FIND_USER_BY_PHONE, User.class);
            query.setParameter(1, phone);
            user = (User) query.getSingleResult();
        return user;
    }

    @Override
    Class<User> getEntityClass() {
        return User.class;
    }
}
