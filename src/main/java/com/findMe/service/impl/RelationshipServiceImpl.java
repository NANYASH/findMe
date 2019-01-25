package com.findMe.service.impl;

import com.findMe.dao.RelationshipDAO;
import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.findMe.validator.RelationshipValidator.validateUpdate;


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

        Relationship relationship = friendsDAO.getRelationshipFromTo(userFromId, userToId);

        if (relationship == null)
            friendsDAO.addRelationship(userFromId, userToId);
        else if (relationship.getRelationshipStatus().equals(RelationshipStatus.DELETED)) {
            friendsDAO.updateRelationship(relationship, RelationshipStatus.REQUESTED);
        } else
            throw new BadRequestException("Action cannot be performed for this user.");
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
    public RelationshipStatus findStatusById(Long userFromId, Long userToId) throws InternalServerError {
        Relationship relationshipStatus = friendsDAO.getRelationship(userFromId, userToId);
        if (relationshipStatus != null)
            return relationshipStatus.getRelationshipStatus();
        return null;
    }

}
