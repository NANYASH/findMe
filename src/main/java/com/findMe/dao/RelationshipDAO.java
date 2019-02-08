package com.findMe.dao;


import com.findMe.model.Relationship;
import com.findMe.model.enums.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;

import java.util.List;

public interface RelationshipDAO {
    void addRelationship(Relationship relationship) throws InternalServerError, BadRequestException;

    void updateRelationship(Long userFromId, Long userToId, Relationship relationship) throws InternalServerError, BadRequestException;

    Relationship getRelationship(Long userFromId, Long userToId) throws InternalServerError;

    List<User> findByRelationshipStatus(Long userId, RelationshipStatus status) throws InternalServerError;

    List<User> findOutgoingRequests(Long userId) throws InternalServerError;

    List<User> findIncomingRequests(Long userId) throws InternalServerError;

    Long getNumberOfRelationships(Long userId, RelationshipStatus status) throws InternalServerError;

    Long getNumberOfOutgoingRequests(Long userId) throws InternalServerError;
}
