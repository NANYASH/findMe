package com.findMe.validator.postValidator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.User;

import java.util.HashSet;
import java.util.Set;

public class UsersTaggedValidator extends AbstractPostChainValidator {

    @Override
    void validate() throws BadRequestException {
        if (super.getPostValidatorRequestData().getUsersTaggedIds().length != 0) {
            Set<User> usersTagged;
            if (super.getPostValidatorRequestData().getPost().getUsersTagged().size() != super.getPostValidatorRequestData().getUsersTaggedIds().length) {
                usersTagged = new HashSet<>(super.getPostValidatorRequestData().getPost().getUsersTagged());

                for (long userTaggedId : super.getPostValidatorRequestData().getUsersTaggedIds()) {
                    if (!usersTagged.contains(userTaggedId))
                        throw new BadRequestException("User with id " + userTaggedId + " does not exist.");
                }
            }
        }
        checkNextValidator(super.getNextValidator());
    }
}
