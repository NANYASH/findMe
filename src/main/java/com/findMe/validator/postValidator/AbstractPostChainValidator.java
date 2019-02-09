package com.findMe.validator.postValidator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.validateData.PostValidatorRequestData;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractPostChainValidator {
    @Getter
    @Setter
    private AbstractPostChainValidator nextValidator;
    @Getter
    @Setter
    private PostValidatorRequestData postValidatorRequestData;

    abstract void validate() throws BadRequestException;

    void checkNextValidator(AbstractPostChainValidator nextValidator) throws BadRequestException {
        if (nextValidator != null)
            nextValidator.validate();
    }
}
