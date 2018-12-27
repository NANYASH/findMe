package com.findMe.service;

import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;

public interface FriendsService {
    void addRelationship(Long userFromId, Long userToId) throws BadRequestException, InternalServerError;
    void deleteRelationship(Long userFromId, Long userToId) throws BadRequestException, InternalServerError;
    void updateRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws BadRequestException, InternalServerError;
}
