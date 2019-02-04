package com.findMe.validator;

import com.findMe.exception.BadRequestException;
import com.findMe.model.RelationshipStatus;

public class AcceptValidator extends AbstractChainValidator {
    private static final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private static final RelationshipStatus NEW_STATUS = RelationshipStatus.ACCEPTED;


    @Override
    void validate() throws BadRequestException {
        if (CURRENT_STATUS.equals(super.getRequestData().getRelationship().getRelationshipStatus()) && NEW_STATUS.equals(super.getRequestData().getNewStatus())) {
                if (super.getRequestData().getNumberOfFriends() < 100)
                    return;
                else
                    throw new BadRequestException("Max number of friends. Action cannot be performed.");
        }

        checkNextValidator(super.getNextValidator());
    }
}
