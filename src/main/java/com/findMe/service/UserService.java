package com.findMe.service;

import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.NotFoundException;
import com.findMe.model.User;

import java.time.LocalDate;


public interface UserService {
    User findUserById(Long id) throws InternalServerError, NotFoundException, BadRequestException;
    User registerUser(User user) throws InternalServerError, BadRequestException;
    User login(String login, String userEnteredPassword) throws InternalServerError, BadRequestException;
    User logout(User user, LocalDate localDate) throws InternalServerError, BadRequestException;

}
