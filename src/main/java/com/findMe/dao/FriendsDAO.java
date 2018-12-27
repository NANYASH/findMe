package com.findMe.dao;


import com.findMe.entity.RelationShipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;

import java.util.List;

public interface FriendsDAO {
    List<User> findFriendsByRelationshipStatus(Long userId, RelationShipStatus status) throws InternalServerError;
    RelationShipStatus checkRelationship(Long userFromId, Long userToId) throws InternalServerError;
    void addRelationship(Long userFromId, Long userToId) throws InternalServerError;
    void deleteRelationship(Long userFromId, Long userToId) throws InternalServerError;
    void updateRelationship(Long userFromId, Long userToId, RelationShipStatus status) throws InternalServerError;
}
