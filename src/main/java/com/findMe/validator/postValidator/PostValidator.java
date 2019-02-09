package com.findMe.validator.postValidator;


import com.findMe.exception.BadRequestException;
import com.findMe.model.Post;
import com.findMe.model.enums.RelationshipStatus;
import com.findMe.model.validateData.PostValidatorRequestData;
import org.springframework.stereotype.Component;


@Component
public class PostValidator {
    public void validatePost(Post post, Long[] usersTaggedIds, RelationshipStatus relationshipStatus) throws BadRequestException {
        PostValidatorRequestData postValidatorRequestData = new PostValidatorRequestData(post,usersTaggedIds,relationshipStatus);

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
