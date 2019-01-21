package com.findMe.service;

import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;

import java.util.List;

public interface RelationshipService {
    void addRelationship(Long userFromId, Long userToId) throws BadRequestException, InternalServerError;
    void updateRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws BadRequestException, InternalServerError;
    RelationshipStatus findStatusById(Long userFromId, Long userToId) throws InternalServerError;
}
