package com.findMe.service.impl;

import com.findMe.dao.RelationshipDAO;
import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;
import com.findMe.service.RelationshipService;
import com.findMe.validator.RelationshipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RelationshipServiceImpl implements RelationshipService {
    private RelationshipDAO relationshipDAO;
    private RelationshipValidator relationshipValidator;

    @Autowired
    public RelationshipServiceImpl(RelationshipDAO relationshipDAO, RelationshipValidator relationshipValidator) {
        this.relationshipDAO = relationshipDAO;
        this.relationshipValidator = relationshipValidator;
    }

    @Override
    public void addRelationship(Long userFromId, Long userToId) throws BadRequestException, InternalServerError {
        if (userFromId.equals(userToId))
            throw new BadRequestException("User cannot add relationship with himself.");

        Relationship relationship = relationshipDAO.getRelationship(userFromId, userToId);

        if (relationship == null)
            relationshipDAO.addRelationship(userFromId, userToId);
        else if (relationship.getRelationshipStatus().equals(RelationshipStatus.DELETED)) {
            relationship = relationshipValidator.validateUpdate(relationship, RelationshipStatus.REQUESTED);
            relationshipDAO.updateRelationship(userFromId, userToId, relationship);
        } else
            throw new BadRequestException("Action cannot be performed for this user.");
    }

    @Override
    public void updateRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws BadRequestException, InternalServerError {
        if (userFromId.equals(userToId))
            throw new BadRequestException("User cannot change relationship with himself.");

        Relationship relationship = relationshipDAO.getRelationshipFromTo(userFromId, userToId);

        relationship = relationshipValidator.validateUpdate(relationship, status);
        relationshipDAO.updateRelationship(userFromId, userToId, relationship);
    }

    @Override
    public void deleteRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws BadRequestException, InternalServerError {
        if (userFromId.equals(userToId))
            throw new BadRequestException("User cannot change relationship with himself.");

        Relationship relationship = relationshipDAO.getRelationship(userFromId, userToId);

        relationship = relationshipValidator.validateUpdate(relationship, status);
        relationshipDAO.updateRelationship(userFromId, userToId, relationship);
    }

    @Override
    public RelationshipStatus findStatusById(Long userFromId, Long userToId) throws InternalServerError {
        Relationship relationshipStatus = relationshipDAO.getRelationship(userFromId, userToId);
        if (relationshipStatus != null)
            return relationshipStatus.getRelationshipStatus();
        return null;
    }

    @Override
    public List<User> findByRelationshipStatus(Long userId, RelationshipStatus status) throws InternalServerError {
        return relationshipDAO.findByRelationshipStatus(userId, status);
    }

    @Override
    public List<User> findRequestedFrom(Long userId) throws InternalServerError {
        return relationshipDAO.findRequestedFrom(userId);
    }

    @Override
    public List<User> findRequestedTo(Long userId) throws InternalServerError {
        return relationshipDAO.findRequestedTo(userId);
    }

}
