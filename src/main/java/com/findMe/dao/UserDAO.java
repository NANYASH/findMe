package com.findMe.dao;

import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;

public interface UserDAO {
    User register(User user) throws InternalServerError, BadRequestException;
    User update(User user)throws InternalServerError;
    void delete(Long id) throws InternalServerError;
    User findById(Long id) throws InternalServerError;
    User findByPhoneAndEmail(String phone,String email) throws InternalServerError;
}
