package com.findMe.service.impl;

import com.findMe.dao.RelationshipDAO;
import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RelationshipServiceImpl implements RelationshipService {
    private RelationshipDAO friendsDAO;

    @Autowired
    public RelationshipServiceImpl(RelationshipDAO friendsDAO) {
        this.friendsDAO = friendsDAO;
    }

    @Override
    public void addRelationship(Long userFromId, Long userToId) throws BadRequestException, InternalServerError {
        if (userFromId.equals(userToId))
            throw new BadRequestException("User cannot add relationship with himself.");
        friendsDAO.addRelationship(userFromId, userToId);
    }

    @Override
    public void updateRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws BadRequestException, InternalServerError {
        if (userFromId.equals(userToId))
            throw new BadRequestException("User cannot change relationship with himself.");
        friendsDAO.updateRelationship(userFromId, userToId, status);
    }

    @Override
    public void deleteRelationship(Long userFromId, Long userToId) throws BadRequestException, InternalServerError {
        if (userFromId.equals(userToId))
            throw new BadRequestException("User cannot delete relationship with himself.");
        friendsDAO.deleteRelationship(userFromId, userToId);
    }

    @Override
    public void rejectRequest(Long userFromId, Long userToId) throws BadRequestException, InternalServerError {
        if (userFromId.equals(userToId))
            throw new BadRequestException("User cannot reject relationship with himself.");
        friendsDAO.rejectRequest(userFromId, userToId);
    }

    @Override
    public RelationshipStatus findStatusById(Long userFromId, Long userToId) throws InternalServerError {
        return friendsDAO.getRelationship(userFromId, userToId).getRelationshipStatus();
    }

}
