package com.findMe.service.impl;

import com.findMe.dao.RelationshipDAO;
import com.findMe.model.Relationship;
import com.findMe.model.RelationshipId;
import com.findMe.model.enums.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;
import com.findMe.service.RelationshipService;
import com.findMe.validator.relationshipValidator.RelationshipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        relationshipValidator.validateUpdate(userFromId, userToId, relationship, RelationshipStatus.REQUESTED,
                relationshipDAO.getNumberOfRelationships(userFromId, RelationshipStatus.ACCEPTED),
                relationshipDAO.getNumberOfOutgoingRequests(userFromId));

        if (relationship == null) {
            relationshipDAO.addRelationship(new Relationship(new RelationshipId(userFromId, userToId), RelationshipStatus.REQUESTED, LocalDate.now()));
        } else {
            relationship.setRelationshipStatus(RelationshipStatus.REQUESTED);
            relationship.setLastUpdateDate(LocalDate.now());
            relationshipDAO.updateRelationship(userFromId, userToId, relationship);
        }
    }

    @Override
    public void updateRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws BadRequestException, InternalServerError {
        if (userFromId.equals(userToId))
            throw new BadRequestException("User cannot change relationship with himself.");

        Relationship relationship = relationshipDAO.getRelationship(userFromId, userToId);

        relationshipValidator.validateUpdate(userFromId, userToId, relationship, status,
                relationshipDAO.getNumberOfRelationships(userToId, RelationshipStatus.ACCEPTED),
                relationshipDAO.getNumberOfOutgoingRequests(userFromId));

        relationship.setRelationshipStatus(status);
        relationship.setLastUpdateDate(LocalDate.now());
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
    public List<User> findOutgoingRequests(Long userId) throws InternalServerError {
        return relationshipDAO.findOutgoingRequests(userId);
    }

    @Override
    public List<User> findIncomingRequests(Long userId) throws InternalServerError {
        return relationshipDAO.findIncomingRequests(userId);
    }

}
