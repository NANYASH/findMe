package com.findMe.service.impl;

import com.findMe.dao.FriendsDAO;
import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;
import com.findMe.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsServiceImpl implements FriendsService{
    private FriendsDAO friendsDAO;

    @Autowired
    public FriendsServiceImpl(FriendsDAO friendsDAO) {
        this.friendsDAO = friendsDAO;
    }

    @Override
    public List<User> findByRelationshipStatus(Long userId, RelationshipStatus status) throws InternalServerError {
        return friendsDAO.findByRelationshipStatus(userId,status);
    }

    @Override
    public List<User> findRequestedFrom(Long userId) throws InternalServerError {
        return friendsDAO.findRequestedFrom(userId);
    }

    @Override
    public List<User> findRequestedTo(Long userId) throws InternalServerError {
        return friendsDAO.findRequestedTo(userId);
    }

    @Override
    public void addRelationship(Long userFromId, Long userToId) throws BadRequestException, InternalServerError {
        if (userFromId == userToId)
            throw  new BadRequestException("User cannot add relationship with himself.");
        friendsDAO.addRelationship(userFromId,userToId);
    }

    @Override
    public void updateRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws BadRequestException, InternalServerError {
        if (userFromId == userToId)
            throw new BadRequestException("User cannot change relationship with himself.");
        friendsDAO.updateRelationship(userFromId,userToId,status);
    }

    @Override
    public void deleteRelationship(Long userFromId, Long userToId) throws BadRequestException, InternalServerError {
        if (userFromId == userToId)
            throw new BadRequestException("User cannot delete relationship with himself.");
        friendsDAO.deleteRelationship(userFromId,userToId);
    }

    @Override
    public void rejectRequest(Long userFromId, Long userToId) throws BadRequestException, InternalServerError {
        if (userFromId == userToId)
            throw new BadRequestException("User cannot reject relationship with himself.");
        friendsDAO.rejectRequest(userFromId,userToId);
    }


}
