package com.findMe.dao;


import com.findMe.entity.Relationship;
import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;

import java.util.List;

public interface RelationshipDAO {
    void addRelationship(Long userFromId, Long userToId) throws InternalServerError, BadRequestException;
    void updateRelationship(Relationship relationship, RelationshipStatus status) throws InternalServerError, BadRequestException;
    Relationship getRelationship(Long userFromId, Long userToId) throws InternalServerError;
    Relationship getRelationshipFromTo(Long userFromId, Long userToId) throws InternalServerError;
}
