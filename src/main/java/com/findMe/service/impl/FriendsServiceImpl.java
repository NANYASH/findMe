package com.findMe.service.impl;

import com.findMe.dao.FriendsDAO;
import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendsServiceImpl implements FriendsService{
    private FriendsDAO friendsDAO;

    @Autowired
    public FriendsServiceImpl(FriendsDAO friendsDAO) {
        this.friendsDAO = friendsDAO;
    }

    @Override
    public void addRelationship(Long userFromId, Long userToId) throws BadRequestException, InternalServerError {
        friendsDAO.addRelationship(userFromId,userToId);
    }

    @Override
    public void deleteRelationship(Long userFromId, Long userToId) throws BadRequestException, InternalServerError {
        friendsDAO.deleteRelationship(userFromId,userToId);
    }

    @Override
    public void updateRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws BadRequestException, InternalServerError {
        friendsDAO.updateRelationship(userFromId,userToId,status);
    }
}
