package com.findMe.service;

import com.findMe.exception.InternalServerError;
import com.findMe.model.User;

public interface UserService {
    User findUserById(Long id) throws InternalServerError;
}
