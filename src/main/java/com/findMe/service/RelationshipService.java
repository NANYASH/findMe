package com.findMe.service;

import com.findMe.model.enums.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;

import java.util.List;

public interface RelationshipService {
    void addRelationship(Long userFromId, Long userToId) throws BadRequestException, InternalServerError;

    void updateRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws BadRequestException, InternalServerError;

    RelationshipStatus findStatusById(Long userFromId, Long userToId) throws InternalServerError;

    List<User> findByRelationshipStatus(Long userId, RelationshipStatus status) throws InternalServerError;

    List<User> findOutgoingRequests(Long userId) throws InternalServerError;

    List<User> findIncomingRequests(Long userId) throws InternalServerError;
}
