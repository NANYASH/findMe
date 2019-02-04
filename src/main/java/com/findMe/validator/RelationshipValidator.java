package com.findMe.validator;


import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class RelationshipValidator {

    public Relationship validateUpdate(Long userFromId, Long userToId,Relationship relationship, RelationshipStatus newStatus, Long numberOfFriends, Long numberOfOutgoingRequests) throws BadRequestException {
        RequestData requestData = new RequestData(userFromId, userToId, relationship,newStatus,numberOfFriends,numberOfOutgoingRequests);
        AbstractChainValidator requestValidator = new RequestValidator();
        AbstractChainValidator rejectValidator = new RejectValidator();
        AbstractChainValidator cancelValidator = new CancelValidator();
        AbstractChainValidator deleteValidator = new DeleteValidator();
        AbstractChainValidator acceptValidator = new AcceptValidator();

        requestValidator.setNextValidator(rejectValidator);
        requestValidator.setRequestData(requestData);

        rejectValidator.setNextValidator(cancelValidator);
        rejectValidator.setRequestData(requestData);

        cancelValidator.setNextValidator(deleteValidator);
        cancelValidator.setRequestData(requestData);

        deleteValidator.setNextValidator(acceptValidator);
        deleteValidator.setRequestData(requestData);

        acceptValidator.setRequestData(requestData);

        requestValidator.validate();

        return relationship;
    }
}
