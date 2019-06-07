package com.findMe.dao;


import com.findMe.model.Relationship;
import com.findMe.model.enums.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;

import java.util.List;

public interface RelationshipDAO {
    Relationship create(Relationship relationship) throws InternalServerError, BadRequestException;

    void update(Long userFromId, Long userToId, Relationship relationship) throws InternalServerError, BadRequestException;

    Relationship getByFromIdToId(Long userFromId, Long userToId) throws InternalServerError;

    List<User> getByStatus(Long userId, RelationshipStatus status) throws InternalServerError;

    List<User> getOutgoingRequests(Long userId) throws InternalServerError;

    List<User> getIncomingRequests(Long userId) throws InternalServerError;

    Long getNumberOfRelationships(Long userId, RelationshipStatus status) throws InternalServerError;

    Long getNumberOfOutgoingRequests(Long userId) throws InternalServerError;
}
