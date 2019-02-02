package com.findMe.validator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.RelationshipStatus;

public class CancelValidator extends AbstractChainValidator{
    private static final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private static final RelationshipStatus NEW_STATUS = RelationshipStatus.DELETED;

    @Override
    void validate() throws BadRequestException {
        if (CURRENT_STATUS.equals(super.getRequestData().getRelationship().getRelationshipStatus()) && NEW_STATUS.equals(super.getRequestData().getNewStatus()))
            return;
        checkNextValidator(super.getNextValidator());
    }

}
