package com.findMe.validator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractChainValidator {
    @Getter
    @Setter
    private AbstractChainValidator nextValidator;
    @Getter
    @Setter
    private RequestData requestData;

    abstract void validate() throws BadRequestException;

    void checkNextValidator(AbstractChainValidator nextValidator) throws BadRequestException {
        if (nextValidator!= null)
            nextValidator.validate();
        else
            throw new BadRequestException("Action cannot be performed to this user.");
    }
}
