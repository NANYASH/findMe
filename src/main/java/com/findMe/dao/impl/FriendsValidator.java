package com.findMe.dao.impl;


import com.findMe.entity.Relationship;
import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;

public class FriendsValidator {

    protected static void validateUpdate(Relationship relationship, RelationshipStatus newStatus) throws BadRequestException {
        if (relationship == null)
            throw new BadRequestException("No requests from this user.");

        if (relationship.getRelationshipStatus() == RelationshipStatus.REQUESTED && newStatus == RelationshipStatus.ACCEPTED)
            return;
        if (relationship.getRelationshipStatus() == RelationshipStatus.REQUESTED && newStatus == RelationshipStatus.REJECTED)
            return;
        if (relationship.getRelationshipStatus() == RelationshipStatus.REJECTED && newStatus == RelationshipStatus.REQUESTED)
            return;

        throw new BadRequestException("Action cannot be performed for this user.");
    }

    protected static void validateDelete(Relationship relationship) throws BadRequestException {
        if (relationship == null)
            throw new BadRequestException("Users are not friends.");

        if (relationship.getRelationshipStatus() == RelationshipStatus.ACCEPTED)
            return;

        throw new BadRequestException("Action cannot be performed for this user.");
    }

    protected static void validateReject(Relationship relationship) throws BadRequestException {
        if (relationship == null || relationship.getRelationshipStatus() != RelationshipStatus.REQUESTED)
            throw new BadRequestException("No requests to this user.");
    }
}
