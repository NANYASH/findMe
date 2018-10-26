package com.findMe.service;


import com.findMe.exception.BadRequestException;
import com.findMe.model.User;

public interface UserService {
    User findUserById(Long id) throws BadRequestException;
}
