package com.findMe.dao.impl;


import com.findMe.dao.UserDAO;
import com.findMe.model.User;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Transactional
@Repository
public class UserDAOImpl extends GenericDAO<User> implements UserDAO {

    @Override
    public void delete(Long id) {
        super.delete(User.class, id);
    }

    @Override
    public User findById(Long id){
        return super.findById(User.class, id);
    }
}
