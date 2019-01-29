package com.findMe.validator;

import com.findMe.exception.BadRequestException;
import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;

public class AcceptValidator extends AbstractChainValidator {
    private static final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private static final RelationshipStatus NEW_STATUS = RelationshipStatus.ACCEPTED;


    @Override
    void validate(Relationship relationship, RelationshipStatus newStatus) throws BadRequestException {
        if (relationship == null)
            throw new BadRequestException("No requests from this user.");

        if (CURRENT_STATUS.equals(relationship.getRelationshipStatus()) && NEW_STATUS.equals(newStatus))
            return;

        if (super.getNextValidator() != null)
            super.getNextValidator() .validate(relationship, newStatus);
        else
            throw new BadRequestException("Action cannot be performed to this user.");
    }
}
