package com.findMe.validator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;

public abstract class AbstractChainValidator {
    private AbstractChainValidator nextValidator;

    abstract void validate(Relationship relationship, RelationshipStatus newStatus, Long numberOfFriends, Long numberOfOutgoingRequests) throws BadRequestException;

    public AbstractChainValidator getNextValidator() {
        return nextValidator;
    }

    public void setNextValidator(AbstractChainValidator nextValidator) {
        this.nextValidator = nextValidator;
    }


}
