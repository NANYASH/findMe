package com.findMe.validator.postValidator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.enums.RelationshipStatus;

public class RelationshipValidator extends AbstractPostChainValidator {

    @Override
    void validate() throws BadRequestException {

        if (!getPostValidatorRequestData().getPost().getUserPosted().equals(getPostValidatorRequestData().getPost().getUserPagePosted())) {
            if (getPostValidatorRequestData().getRelationship().getRelationshipStatus() != null
                    && getPostValidatorRequestData().getRelationship().getRelationshipStatus().equals(RelationshipStatus.ACCEPTED)) {
                checkNextValidator(getNextValidator());

            } else
                throw new BadRequestException("Users are not friends. Action cannot be performed.");
        }
        checkNextValidator(super.getNextValidator());
    }
}
