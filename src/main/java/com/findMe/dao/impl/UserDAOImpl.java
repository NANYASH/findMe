package com.findMe.dao.impl;


import com.findMe.dao.UserDAO;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Transactional
@Repository
public class UserDAOImpl extends GenericDAO<User> implements UserDAO {

    @Override
    public void delete(Long id) throws InternalServerError {
        super.delete(id);
    }

    @Override
    public User findById(Long id) throws InternalServerError {
        return super.findById(id);
    }

    @Override
    Class<User> getEntityClass() {
        return User.class;
    }
}
