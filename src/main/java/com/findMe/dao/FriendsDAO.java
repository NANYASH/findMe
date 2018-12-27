package com.findMe.dao;


import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;

import java.util.List;

public interface FriendsDAO {
    List<User> findFriendsByRelationshipStatus(Long userId, RelationshipStatus status) throws InternalServerError;
    RelationshipStatus getRelationship(Long userFromId, Long userToId) throws InternalServerError;
    void addRelationship(Long userFromId, Long userToId) throws InternalServerError, BadRequestException;
    void deleteRelationship(Long userFromId, Long userToId) throws InternalServerError, BadRequestException;
    void updateRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws InternalServerError, BadRequestException;
}
