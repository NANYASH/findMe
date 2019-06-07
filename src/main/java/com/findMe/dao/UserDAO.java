package com.findMe.dao;

import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;


public interface UserDAO {
    User add(User user) throws InternalServerError, BadRequestException;

    User update(User user) throws InternalServerError;

    void delete(Long id) throws InternalServerError;

    User getById(Long id) throws InternalServerError;

    User getByPhoneOrEmail(String phone, String email) throws InternalServerError;
}
