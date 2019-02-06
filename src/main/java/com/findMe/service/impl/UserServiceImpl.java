package com.findMe.service.impl;


import com.findMe.dao.UserDAO;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.NotFoundException;
import com.findMe.model.User;
import com.findMe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User findUserById(Long id) throws InternalServerError, NotFoundException, BadRequestException {
        User userFound = userDAO.findById(id);
        if (userFound == null)
            throw new NotFoundException("User with such id cannot be found.");
        return userFound;
    }

    @Override
    public User registerUser(User user) throws InternalServerError, BadRequestException {
        user.setDateRegistered(LocalDate.now());
        return userDAO.register(user);
    }

    @Override
    public User login(String login, String userEnteredPassword) throws InternalServerError, BadRequestException {
        User user = userDAO.findByPhoneAndEmail(login, login);
        if (user != null && user.getPassword().equals(userEnteredPassword))
            return user;
        throw new BadRequestException("Incorrect credentials.");
    }

    @Override
    public User logout(User user, LocalDate localDate) throws InternalServerError, BadRequestException {
        user.setDateLastActive(LocalDate.now());
        return userDAO.update(user);
    }

}
