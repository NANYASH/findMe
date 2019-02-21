package com.findMe.validator.postValidator;


import com.findMe.exception.BadRequestException;

public class TextValidator extends AbstractPostChainValidator {

    @Override
    void validate() throws BadRequestException {
        if (getPostValidatorRequestData().getPost().getText().length() > 200)
            throw new BadRequestException("Too long text. Action cannot be performed");

        if (getPostValidatorRequestData().getPost().getText().contains("http") || getPostValidatorRequestData().getPost().getText().contains("www"))
            throw new BadRequestException("Invalid text. Action cannot be performed");

        checkNextValidator(super.getNextValidator());
    }
}
