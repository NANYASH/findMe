package com.findMe.validator;


import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;
import com.findMe.exception.BadRequestException;

public class RelationshipValidator {

    public static void validateUpdate(Relationship relationship, RelationshipStatus newStatus) throws BadRequestException {
        if (relationship == null)
            throw new BadRequestException("No requests from this user.");

        if (relationship.getRelationshipStatus().equals(RelationshipStatus.REQUESTED) && newStatus.equals(RelationshipStatus.ACCEPTED))
            return;
        if (relationship.getRelationshipStatus().equals(RelationshipStatus.REQUESTED) && newStatus.equals(RelationshipStatus.REJECTED))
            return;
        if (relationship.getRelationshipStatus().equals(RelationshipStatus.REQUESTED) && newStatus.equals(RelationshipStatus.DELETED))
            return;
        if (relationship.getRelationshipStatus().equals(RelationshipStatus.ACCEPTED) && newStatus.equals(RelationshipStatus.DELETED))
            return;
        if (relationship.getRelationshipStatus().equals(RelationshipStatus.DELETED) && newStatus.equals(RelationshipStatus.REQUESTED))
            return;

        throw new BadRequestException("Action cannot be performed to this user.");
    }
}
