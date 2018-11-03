package com.findMe.service;

import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.NotFoundException;
import com.findMe.model.User;

public interface UserService {
    User findUserById(String id) throws InternalServerError, NotFoundException, BadRequestException;
    User registerUser(User user) throws InternalServerError, BadRequestException;
}
