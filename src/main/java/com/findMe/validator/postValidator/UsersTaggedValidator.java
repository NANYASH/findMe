package com.findMe.validator.postValidator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.User;

import java.util.HashSet;
import java.util.Set;

public class UsersTaggedValidator extends AbstractPostChainValidator {

    @Override
    void validate() throws BadRequestException {
        if (getPostValidatorRequestData().getUsersTaggedIds().length != 0) {
            Set<User> usersTagged;
            if (getPostValidatorRequestData().getPost().getUsersTagged().size() != getPostValidatorRequestData().getUsersTaggedIds().length) {
                usersTagged = new HashSet<>(getPostValidatorRequestData().getPost().getUsersTagged());

                for (long userTaggedId : getPostValidatorRequestData().getUsersTaggedIds()) {
                    if (!usersTagged.contains(userTaggedId))
                        throw new BadRequestException("User with id " + userTaggedId + " does not exist.");
                }
            }
        }
        checkNextValidator(super.getNextValidator());
    }
}
