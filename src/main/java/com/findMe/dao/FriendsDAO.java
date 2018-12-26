package com.findMe.dao;


import com.findMe.entity.RelationShipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.model.User;

import java.util.List;

public interface FriendsDAO {
    List<User> findFriendsByRelationshipStatus(Long userId, RelationShipStatus status);
    RelationShipStatus checkRelationship(Long userFromId, Long userToId) throws BadRequestException;
    void addRelationship(Long userFromId, Long userToId);
    void deleteRelationship(Long userFromId, Long userToId);
    void updateRelationship(Long userFromId, Long userToId, RelationShipStatus status);
}
