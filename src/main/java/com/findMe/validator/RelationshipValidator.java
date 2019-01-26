package com.findMe.validator;


import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class RelationshipValidator {

   /* public static void validateUpdate(Relationship relationship, RelationshipStatus newStatus) throws BadRequestException {
        if (relationship == null)
            throw new BadRequestException("No requests from this user.");

        if (relationship.getRelationshipStatus().equals(RelationshipStatus.REQUESTED) && newStatus.equals(RelationshipStatus.ACCEPTED))
            return;
        if (relationship.getRelationshipStatus().equals(RelationshipStatus.REQUESTED) && newStatus.equals(RelationshipStatus.REJECTED))
            return;
        if (relationship.getRelationshipStatus().equals(RelationshipStatus.REQUESTED) && newStatus.equals(RelationshipStatus.DELETED))
            return;
        if (relationship.getRelationshipStatus().equals(RelationshipStatus.ACCEPTED) && newStatus.equals(RelationshipStatus.DELETED))
            return;
        if (relationship.getRelationshipStatus().equals(RelationshipStatus.DELETED) && newStatus.equals(RelationshipStatus.REQUESTED))
            return;

        throw new BadRequestException("Action cannot be performed to this user.");
    }*/

    public Relationship validateUpdate(Relationship relationship, RelationshipStatus newStatus) throws BadRequestException {
        AbstractChainValidator acceptValidator = new AcceptValidator();
        AbstractChainValidator rejectValidator = new RejectValidator();
        AbstractChainValidator cancelValidator = new CancelValidator();
        AbstractChainValidator deleteValidator = new DeleteValidator();
        AbstractChainValidator requestValidator = new RequestValidator();

        acceptValidator.setNextValidator(rejectValidator);
        rejectValidator.setNextValidator(cancelValidator);
        cancelValidator.setNextValidator(deleteValidator);
        deleteValidator.setNextValidator(requestValidator);

        acceptValidator.validate(relationship,newStatus);

        return relationship;
    }
}
