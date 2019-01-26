package com.findMe.validator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;

public abstract class AbstractChainValidator {
    private AbstractChainValidator nextValidator;

    public Relationship validate(Relationship relationship, RelationshipStatus newStatus) throws BadRequestException {
        if (relationship == null)
            throw new BadRequestException("No requests from this user.");

        if (getCurrentStatus().equals(relationship.getRelationshipStatus()) && getNewStatusStatus().equals(newStatus)) {
            relationship.setRelationshipStatus(newStatus);
            return relationship;
        }

        if (nextValidator != null)
            return nextValidator.validate(relationship, newStatus);
        else
            throw new BadRequestException("Action cannot be performed to this user.");
    }

    public void setNextValidator(AbstractChainValidator nextValidator) {
        this.nextValidator = nextValidator;
    }

    abstract RelationshipStatus getCurrentStatus();

    abstract RelationshipStatus getNewStatusStatus();

}
