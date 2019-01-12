package com.findMe.dao.impl;


import com.findMe.entity.Relationship;
import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;

public class RelationshipValidator {

    public static void validateUpdate(Relationship relationship, RelationshipStatus newStatus) throws BadRequestException {
        if (relationship == null)
            throw new BadRequestException("No requests from this user.");

        if (relationship.getRelationshipStatus().equals(RelationshipStatus.REQUESTED) && newStatus.equals(RelationshipStatus.ACCEPTED))
            return;
        if (relationship.getRelationshipStatus().equals(RelationshipStatus.REQUESTED) && newStatus.equals(RelationshipStatus.REJECTED))
            return;
        /*if (relationship.getRelationshipStatus() == RelationshipStatus.REJECTED && newStatus == RelationshipStatus.REQUESTED)
            return;*/

        throw new BadRequestException("Action cannot be performed for this user.");
    }

    public static void validateDelete(Relationship relationship) throws BadRequestException {
        if (relationship == null)
            throw new BadRequestException("Users are not friends.");

        if (relationship.getRelationshipStatus().equals(RelationshipStatus.ACCEPTED))
            return;

        throw new BadRequestException("Action cannot be performed for this user.");
    }

    public static void validateReject(Relationship relationship) throws BadRequestException {
        if (relationship == null || !relationship.getRelationshipStatus().equals(RelationshipStatus.REQUESTED))
            throw new BadRequestException("No requests to this user.");
    }
}
