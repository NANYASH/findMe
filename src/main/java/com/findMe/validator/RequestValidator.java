package com.findMe.validator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.RelationshipStatus;

public class RequestValidator extends AbstractChainValidator {
    private static final RelationshipStatus CURRENT_STATUS = RelationshipStatus.DELETED;
    private static final RelationshipStatus NEW_STATUS = RelationshipStatus.REQUESTED;


    @Override
    void validate() throws BadRequestException {
        if (super.getRequestData().getRelationship()== null) {
            if (super.getRequestData().getNewStatus().equals(NEW_STATUS) && super.getRequestData().getNumberOfOutgoingRequests() < 1)
                return;
            else
                throw new BadRequestException("Max number of outgoing requests. Action cannot be performed.");
        }

        if (CURRENT_STATUS.equals(super.getRequestData().getRelationship().getRelationshipStatus()) && NEW_STATUS.equals(super.getRequestData().getNewStatus())) {
            if (super.getRequestData().getNumberOfOutgoingRequests() < 1)
                return;
            else
                throw new BadRequestException("Max number of outgoing requests. Action cannot be performed.");
        }

        checkNextValidator(super.getNextValidator());
    }

}
