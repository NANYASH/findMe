package com.findMe.validator.relationshipValidator;

import com.findMe.exception.BadRequestException;
import com.findMe.model.enums.RelationshipStatus;

public class AcceptValidator extends AbstractRelationshipChainValidator {
    private static final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private static final RelationshipStatus NEW_STATUS = RelationshipStatus.ACCEPTED;

    @Override
    void validate() throws BadRequestException {
        if (CURRENT_STATUS.equals(this.getRelationshipValidatorRequestData().getRelationship().getRelationshipStatus()) && NEW_STATUS.equals(this.getRelationshipValidatorRequestData().getNewStatus())) {
            if (this.getRelationshipValidatorRequestData().getRelationship().getRelationshipId().getUserToId().equals(this.getRelationshipValidatorRequestData().getUserToId())) {

                if (this.getRelationshipValidatorRequestData().getNumberOfFriends() < 100)
                    return;
                else
                    throw new BadRequestException("Max number of friends. Action cannot be performed.");
            }
        }

        checkNextValidator(super.getNextValidator());
    }
}
