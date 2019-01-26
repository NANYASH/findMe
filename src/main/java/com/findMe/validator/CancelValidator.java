package com.findMe.validator;


import com.findMe.model.RelationshipStatus;

public class CancelValidator extends AbstractChainValidator{

    @Override
    RelationshipStatus getCurrentStatus() {
        return RelationshipStatus.REQUESTED;
    }

    @Override
    RelationshipStatus getNewStatusStatus() {
        return RelationshipStatus.DELETED;
    }
}
