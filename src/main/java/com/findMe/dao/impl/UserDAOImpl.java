package com.findMe.dao.impl;


import com.findMe.dao.UserDAO;
import com.findMe.exception.BadRequestException;
import com.findMe.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@Transactional
@Repository
public class UserDAOImpl extends GenericDAO<User> implements UserDAO{
    
    @Override
    public void delete(Long id) {
        super.delete(User.class,id);
    }

    @Override
    public User findById(Long id) throws BadRequestException {
        try {
            return super.findById(User.class,id);
        }catch (NoResultException e){
            throw new BadRequestException("No users with such id");
        }
    }
}
