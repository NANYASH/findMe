package com.findMe.validator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;

public class RequestValidator extends AbstractChainValidator {
    private static final RelationshipStatus CURRENT_STATUS = RelationshipStatus.DELETED;
    private static final RelationshipStatus NEW_STATUS = RelationshipStatus.REQUESTED;


    @Override
    void validate(Relationship relationship, RelationshipStatus newStatus, Long numberOfFriends, Long numberOfOutgoingRequests) throws BadRequestException {
        if (relationship == null) {
            if (newStatus.equals(NEW_STATUS) && numberOfOutgoingRequests < 1)
                return;
            else
                throw new BadRequestException("Max number of outgoing requests. Action cannot be performed.");
        }

        if (CURRENT_STATUS.equals(relationship.getRelationshipStatus()) && NEW_STATUS.equals(newStatus)) {
            if (numberOfOutgoingRequests < 10)
                return;
            else
                throw new BadRequestException("Max number of outgoing requests. Action cannot be performed.");
        }

        if (super.getNextValidator() != null)
            super.getNextValidator().validate(relationship, newStatus, numberOfFriends, numberOfOutgoingRequests);
        else
            throw new BadRequestException("Action cannot be performed to this user.");
    }

}
