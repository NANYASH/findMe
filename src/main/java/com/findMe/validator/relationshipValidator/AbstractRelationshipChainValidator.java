package com.findMe.validator.relationshipValidator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.validateData.RelationshipValidatorRequestData;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractRelationshipChainValidator {
    @Getter
    @Setter
    private AbstractRelationshipChainValidator nextValidator;
    @Getter
    @Setter
    private RelationshipValidatorRequestData relationshipValidatorRequestData;

    abstract void validate() throws BadRequestException;

    void checkNextValidator(AbstractRelationshipChainValidator nextValidator) throws BadRequestException {
        if (nextValidator!= null)
            nextValidator.validate();
        else
            throw new BadRequestException("Action cannot be performed to this user.");
    }
}
