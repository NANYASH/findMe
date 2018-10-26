package com.findMe.service.impl;


import com.findMe.dao.UserDAO;
import com.findMe.exception.BadRequestException;
import com.findMe.model.User;
import com.findMe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User findUserById(Long id) throws BadRequestException {
        return userDAO.findById(id);
    }
}
