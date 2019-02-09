package com.findMe.validator.postValidator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.enums.RelationshipStatus;

public class RelationshipValidator extends AbstractPostChainValidator {

    @Override
    void validate() throws BadRequestException {

        if (!super.getPostValidatorRequestData().getPost().getUserPosted().equals(super.getPostValidatorRequestData().getPost().getUserPagePosted())) {

            if (super.getPostValidatorRequestData().getRelationship() != null
                    && super.getPostValidatorRequestData().getRelationship().getRelationshipStatus().equals(RelationshipStatus.ACCEPTED)) {
                checkNextValidator(super.getNextValidator());

            } else
                throw new BadRequestException("Users are not friends. Action cannot be performed.");
        }
        checkNextValidator(super.getNextValidator());
    }
}
