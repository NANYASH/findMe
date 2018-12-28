package com.findMe.service;

import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;

import java.util.List;

public interface FriendsService {
    List<User> findByRelationshipStatus(Long userId, RelationshipStatus status) throws InternalServerError;
    List<User> findRequestedFrom(Long userId, RelationshipStatus status) throws InternalServerError;
    List<User> findRequestedTo(Long userId, RelationshipStatus status) throws InternalServerError;
    void addRelationship(Long userFromId, Long userToId) throws BadRequestException, InternalServerError;
    void deleteRelationship(Long userFromId, Long userToId) throws BadRequestException, InternalServerError;
    void updateRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws BadRequestException, InternalServerError;
    void rejectRequest(Long userFromId, Long userToId) throws InternalServerError, BadRequestException;
}
