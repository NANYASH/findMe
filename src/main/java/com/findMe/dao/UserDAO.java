package com.findMe.dao;


import com.findMe.exception.BadRequestException;
import com.findMe.model.User;

public interface UserDAO {
    User findById(Long id) throws BadRequestException;
}
