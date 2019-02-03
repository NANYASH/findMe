package com.findMe.validator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.RelationshipStatus;

import java.time.LocalDate;


public class DeleteValidator extends AbstractChainValidator {
    private static final RelationshipStatus CURRENT_STATUS = RelationshipStatus.ACCEPTED;
    private static final RelationshipStatus NEW_STATUS = RelationshipStatus.DELETED;


    @Override
    void validate() throws BadRequestException {
        if (CURRENT_STATUS.equals(super.getRequestData().getRelationship().getRelationshipStatus()) && NEW_STATUS.equals(super.getRequestData().getNewStatus())) {
            if (super.getRequestData().getRelationship().getLastUpdateDate().plusDays(3).isBefore(LocalDate.now()))
                return;
            else
                throw new BadRequestException("User could be deleted in 3 days from adding. Action cannot be performed.");
        }

        checkNextValidator(super.getNextValidator());
    }

}
