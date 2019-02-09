package com.findMe.validator.postValidator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.Post;
import com.findMe.model.Relationship;
import com.findMe.model.validateData.PostValidatorRequestData;
import org.springframework.stereotype.Component;


@Component
public class PostValidator {
    public void validatePost(Post post, Long[] usersTaggedIds, Relationship relationship) throws BadRequestException {
        PostValidatorRequestData postValidatorRequestData = new PostValidatorRequestData(post,usersTaggedIds,relationship);

        AbstractPostChainValidator relationshipValidator= new RelationshipValidator();
        AbstractPostChainValidator textValidator= new TextValidator();
        AbstractPostChainValidator usersTaggedValidator = new UsersTaggedValidator();

        relationshipValidator.setNextValidator(textValidator);
        relationshipValidator.setPostValidatorRequestData(postValidatorRequestData);

        textValidator.setNextValidator(usersTaggedValidator);
        textValidator.setPostValidatorRequestData(postValidatorRequestData);

        usersTaggedValidator.setPostValidatorRequestData(postValidatorRequestData);

        relationshipValidator.validate();
    }
}
