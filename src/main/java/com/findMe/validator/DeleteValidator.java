package com.findMe.validator;


import com.findMe.model.RelationshipStatus;

public class DeleteValidator extends AbstractChainValidator{

    @Override
    RelationshipStatus getCurrentStatus() {
        return RelationshipStatus.ACCEPTED;
    }

    @Override
    RelationshipStatus getNewStatusStatus() {
        return RelationshipStatus.DELETED;
    }
}
