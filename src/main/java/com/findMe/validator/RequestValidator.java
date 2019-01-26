package com.findMe.validator;



import com.findMe.model.RelationshipStatus;

public class RequestValidator extends AbstractChainValidator{

    @Override
    RelationshipStatus getCurrentStatus() {
        return RelationshipStatus.DELETED;
    }

    @Override
    RelationshipStatus getNewStatusStatus() {
        return RelationshipStatus.REQUESTED;
    }
}
