package com.findMe.validator;



import com.findMe.exception.BadRequestException;
import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;

public class RejectValidator extends AbstractChainValidator {
    private static final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private static final RelationshipStatus NEW_STATUS = RelationshipStatus.REJECTED;


    @Override
    void validate(Relationship relationship, RelationshipStatus newStatus, Long numberOfFriends, Long numberOfOutgoingRequests) throws BadRequestException {
        if (CURRENT_STATUS.equals(relationship.getRelationshipStatus()) && NEW_STATUS.equals(newStatus))
            return;

        if (super.getNextValidator()  != null)
             super.getNextValidator() .validate(relationship, newStatus, numberOfFriends, numberOfOutgoingRequests);
        else
            throw new BadRequestException("Action cannot be performed to this user.");
    }

}
