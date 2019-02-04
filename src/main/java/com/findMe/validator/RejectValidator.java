package com.findMe.validator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.RelationshipStatus;

public class RejectValidator extends AbstractChainValidator {
    private static final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private static final RelationshipStatus NEW_STATUS = RelationshipStatus.REJECTED;


    @Override
    void validate() throws BadRequestException {
        if (CURRENT_STATUS.equals(super.getRequestData().getRelationship().getRelationshipStatus()) && NEW_STATUS.equals(super.getRequestData().getNewStatus()))
            if (super.getRequestData().getRelationship().getRelationshipId().getUserToId().equals(super.getRequestData().getUserToId()))
                return;

        checkNextValidator(super.getNextValidator());
    }

}
