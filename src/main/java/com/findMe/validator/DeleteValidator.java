package com.findMe.validator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;

import java.time.LocalDate;


public class DeleteValidator extends AbstractChainValidator {
    private static final RelationshipStatus CURRENT_STATUS = RelationshipStatus.ACCEPTED;
    private static final RelationshipStatus NEW_STATUS = RelationshipStatus.DELETED;


    @Override
    void validate(Relationship relationship, RelationshipStatus newStatus, Long numberOfFriends, Long numberOfOutgoingRequests) throws BadRequestException {
        if (CURRENT_STATUS.equals(relationship.getRelationshipStatus()) && NEW_STATUS.equals(newStatus)) {
            if (relationship.getLastUpdateDate().plusDays(3).isBefore(LocalDate.now()))
                return;
            else
                throw new BadRequestException("User could be deleted in 3 days from adding. Action cannot be performed.");
        }

        if (super.getNextValidator() != null)
            super.getNextValidator().validate(relationship, newStatus, numberOfFriends, numberOfOutgoingRequests);
        else
            throw new BadRequestException("Action cannot be performed to this user.");
    }

}
