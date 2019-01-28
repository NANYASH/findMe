package com.findMe.validator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;

public abstract class AbstractChainValidator {
    private AbstractChainValidator nextValidator;

    abstract Relationship validate(Relationship relationship, RelationshipStatus newStatus) throws BadRequestException;

    public AbstractChainValidator getNextValidator() {
        return nextValidator;
    }

    public void setNextValidator(AbstractChainValidator nextValidator) {
        this.nextValidator = nextValidator;
    }


}
