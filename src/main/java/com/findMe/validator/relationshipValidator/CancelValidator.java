package com.findMe.validator.relationshipValidator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.enums.RelationshipStatus;

public class CancelValidator extends AbstractRelationshipChainValidator {
    private static final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private static final RelationshipStatus NEW_STATUS = RelationshipStatus.CANCELED;

    @Override
    void validate() throws BadRequestException {
        if (CURRENT_STATUS.equals(this.getRelationshipValidatorRequestData().getRelationship().getRelationshipStatus()) && NEW_STATUS.equals(this.getRelationshipValidatorRequestData().getNewStatus()))
            if (this.getRelationshipValidatorRequestData().getRelationship().getRelationshipId().getUserFromId().equals(this.getRelationshipValidatorRequestData().getUserFromId()))
                return;

        checkNextValidator(super.getNextValidator());
    }

}
