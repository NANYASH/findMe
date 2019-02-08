package com.findMe.validator.relationshipValidator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.enums.RelationshipStatus;

import java.time.LocalDate;


public class DeleteValidator extends AbstractRelationshipChainValidator {
    private static final RelationshipStatus CURRENT_STATUS = RelationshipStatus.ACCEPTED;
    private static final RelationshipStatus NEW_STATUS = RelationshipStatus.DELETED;

    @Override
    void validate() throws BadRequestException {
        if (CURRENT_STATUS.equals(this.getRelationshipValidatorRequestData().getRelationship().getRelationshipStatus()) && NEW_STATUS.equals(this.getRelationshipValidatorRequestData().getNewStatus())) {
            if (this.getRelationshipValidatorRequestData().getRelationship().getLastUpdateDate().plusDays(3).isBefore(LocalDate.now()))
                return;
            else
                throw new BadRequestException("User could be deleted in 3 days from adding. Action cannot be performed.");
        }

        checkNextValidator(super.getNextValidator());
    }

}
