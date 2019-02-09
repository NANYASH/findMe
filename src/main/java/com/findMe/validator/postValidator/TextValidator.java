package com.findMe.validator.postValidator;


import com.findMe.exception.BadRequestException;

public class TextValidator extends AbstractPostChainValidator {

    @Override
    void validate() throws BadRequestException {
        if (super.getPostValidatorRequestData().getPost().getText().length() > 200)
            throw new BadRequestException("Too long text. Action cannot be performed");

        if (super.getPostValidatorRequestData().getPost().getText().contains("http") || super.getPostValidatorRequestData().getPost().getText().contains("www"))
            throw new BadRequestException("Invalid text. Action cannot be performed");

        checkNextValidator(super.getNextValidator());
    }
}
