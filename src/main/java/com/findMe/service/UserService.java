package com.findMe.service;

import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.NotFoundException;
import com.findMe.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Long id) throws InternalServerError, NotFoundException, BadRequestException;
    User registerUser(User user) throws InternalServerError, BadRequestException;
    User login(String login, String userEnteredPassword) throws InternalServerError, BadRequestException;
    List<User> findByRelationshipStatus(Long userId, RelationshipStatus status) throws InternalServerError;
    List<User> findRequestedFrom(Long userId) throws InternalServerError;
    List<User> findRequestedTo(Long userId) throws InternalServerError;

}
