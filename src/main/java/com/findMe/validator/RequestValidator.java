package com.findMe.validator;



import com.findMe.exception.BadRequestException;
import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;

public class RequestValidator extends AbstractChainValidator{
    private static final RelationshipStatus currentStatus = RelationshipStatus.DELETED;
    private static final RelationshipStatus newStatus = RelationshipStatus.REQUESTED;


    @Override
    Relationship validate(Relationship relationship, RelationshipStatus newStatus) throws BadRequestException {
        if (relationship == null)
            throw new BadRequestException("No requests from this user.");

        if (currentStatus.equals(newStatus)) {
            relationship.setRelationshipStatus(newStatus);
            return relationship;
        }

        if (super.getNextValidator()  != null)
            return super.getNextValidator() .validate(relationship, newStatus);
        else
            throw new BadRequestException("Action cannot be performed to this user.");
    }

}
