package com.findMe.validator.relationshipValidator;


import com.findMe.model.Relationship;
import com.findMe.model.enums.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.model.validateData.RelationshipValidatorRequestData;
import org.springframework.stereotype.Component;

@Component
public class RelationshipValidator {

    public Relationship validateUpdate(RelationshipValidatorRequestData relationshipValidatorRequestData) throws BadRequestException {
        AbstractRelationshipChainValidator requestValidator = new RequestValidator();
        AbstractRelationshipChainValidator rejectValidator = new RejectValidator();
        AbstractRelationshipChainValidator cancelValidator = new CancelValidator();
        AbstractRelationshipChainValidator deleteValidator = new DeleteValidator();
        AbstractRelationshipChainValidator acceptValidator = new AcceptValidator();

        requestValidator.setNextValidator(rejectValidator);
        requestValidator.setRelationshipValidatorRequestData(relationshipValidatorRequestData);

        rejectValidator.setNextValidator(cancelValidator);
        rejectValidator.setRelationshipValidatorRequestData(relationshipValidatorRequestData);

        cancelValidator.setNextValidator(deleteValidator);
        cancelValidator.setRelationshipValidatorRequestData(relationshipValidatorRequestData);

        deleteValidator.setNextValidator(acceptValidator);
        deleteValidator.setRelationshipValidatorRequestData(relationshipValidatorRequestData);

        acceptValidator.setRelationshipValidatorRequestData(relationshipValidatorRequestData);

        requestValidator.validate();

        return relationshipValidatorRequestData.getRelationship();
    }
}
