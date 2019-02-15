package com.findMe.validator.postValidator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsersTaggedValidator extends AbstractPostChainValidator {

    @Override
    void validate() throws BadRequestException {
        if (getPostValidatorRequestData().getUsersTaggedIds().length != 0) {
            Set<Long> usersTaggedIds;
            if (getPostValidatorRequestData().getPost().getUsersTagged().size() != getPostValidatorRequestData().getUsersTaggedIds().length) {
                usersTaggedIds = getUsersIds(getPostValidatorRequestData().getPost().getUsersTagged());
                for (long userTaggedId : getPostValidatorRequestData().getUsersTaggedIds()) {
                    if (!usersTaggedIds.contains(userTaggedId))
                        throw new BadRequestException("User with id " + userTaggedId + " is not your friend.");
                }
            }
        }
        checkNextValidator(super.getNextValidator());
    }

    private Set<Long> getUsersIds(List<User> usersFound) {
        Set<Long> usersFoundIds = new HashSet<>();
        for (User user : usersFound)
            usersFoundIds.add(user.getId());
        return usersFoundIds;
    }
}
