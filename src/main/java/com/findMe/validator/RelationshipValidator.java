package com.findMe.validator;


import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class RelationshipValidator {

    public Relationship validateUpdate(Relationship relationship, RelationshipStatus newStatus, Long numberOfFriends, Long numberOfOutgoingRequests) throws BadRequestException {
        AbstractChainValidator requestValidator = new RequestValidator();
        AbstractChainValidator rejectValidator = new RejectValidator();
        AbstractChainValidator cancelValidator = new CancelValidator();
        AbstractChainValidator deleteValidator = new DeleteValidator();
        AbstractChainValidator acceptValidator = new AcceptValidator();

        requestValidator.setNextValidator(rejectValidator);
        rejectValidator.setNextValidator(cancelValidator);
        cancelValidator.setNextValidator(deleteValidator);
        deleteValidator.setNextValidator(acceptValidator);

        requestValidator.validate(relationship,newStatus , numberOfFriends, numberOfOutgoingRequests);

        return relationship;
    }
}
