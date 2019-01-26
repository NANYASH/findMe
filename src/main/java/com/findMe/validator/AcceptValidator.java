package com.findMe.validator;

import com.findMe.model.RelationshipStatus;

public class AcceptValidator extends AbstractChainValidator {


    @Override
    RelationshipStatus getCurrentStatus() {
        return RelationshipStatus.REQUESTED;
    }

    @Override
    RelationshipStatus getNewStatusStatus() {
        return RelationshipStatus.ACCEPTED;
    }
}
