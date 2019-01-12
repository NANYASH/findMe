package com.findMe.service.impl;

import com.findMe.dao.RelationshipDAO;
import com.findMe.entity.Relationship;
import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.findMe.service.RelationshipValidator.validateDelete;
import static com.findMe.service.RelationshipValidator.validateReject;
import static com.findMe.service.RelationshipValidator.validateUpdate;


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

        Relationship relationship = friendsDAO.getRelationshipFromTo(userFromId, userToId);
        validateUpdate(relationship, status);

        friendsDAO.updateRelationship(relationship, status);
    }

    @Override
    public void deleteRelationship(Long userFromId, Long userToId) throws BadRequestException, InternalServerError {
        if (userFromId.equals(userToId))
            throw new BadRequestException("User cannot delete relationship with himself.");

        Relationship relationship = friendsDAO.getRelationship(userFromId, userToId);
        validateDelete(relationship);

        friendsDAO.updateRelationship(relationship, RelationshipStatus.DELETED);
    }

    @Override
    public void rejectRequest(Long userFromId, Long userToId) throws BadRequestException, InternalServerError {
        if (userFromId.equals(userToId))
            throw new BadRequestException("User cannot reject relationship with himself.");

        Relationship relationship = friendsDAO.getRelationshipFromTo(userFromId, userToId);
        validateReject(friendsDAO.getRelationshipFromTo(userFromId, userToId));

        friendsDAO.updateRelationship(relationship, RelationshipStatus.DELETED);
    }

    @Override
    public RelationshipStatus findStatusById(Long userFromId, Long userToId) throws InternalServerError {
        Relationship relationshipStatus = friendsDAO.getRelationship(userFromId, userToId);
        if (relationshipStatus != null)
            return relationshipStatus.getRelationshipStatus();
        return RelationshipStatus.NOT_FRIENDS;
    }

}
