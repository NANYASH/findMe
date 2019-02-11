package com.findMe.validator.relationshipValidator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.enums.RelationshipStatus;

public class RequestValidator extends AbstractRelationshipChainValidator {
    private static final RelationshipStatus CURRENT_STATUS1 = RelationshipStatus.DELETED;
    private static final RelationshipStatus CURRENT_STATUS2 = RelationshipStatus.CANCELED;
    private static final RelationshipStatus NEW_STATUS = RelationshipStatus.REQUESTED;


    @Override
    void validate() throws BadRequestException {
        if (this.getRelationshipValidatorRequestData().getRelationship() == null) {
            if (this.getRelationshipValidatorRequestData().getNewStatus().equals(NEW_STATUS) && this.getRelationshipValidatorRequestData().getNumberOfOutgoingRequests() < 10)
                return;
            else
                throw new BadRequestException("Max number of outgoing requests. Action cannot be performed.");
        }

        if ((CURRENT_STATUS1.equals(this.getRelationshipValidatorRequestData().getRelationship().getRelationshipStatus())
                || CURRENT_STATUS2.equals(this.getRelationshipValidatorRequestData().getRelationship().getRelationshipStatus()))
                && NEW_STATUS.equals(this.getRelationshipValidatorRequestData().getNewStatus())){
            if (this.getRelationshipValidatorRequestData().getNumberOfOutgoingRequests() < 10)
                return;
            else
                throw new BadRequestException("Max number of outgoing requests. Action cannot be performed.");
        }
        checkNextValidator(super.getNextValidator());
    }

}
