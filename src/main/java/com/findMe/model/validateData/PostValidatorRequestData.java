package com.findMe.model.validateData;

import com.findMe.model.Post;
import com.findMe.model.enums.RelationshipStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class PostValidatorRequestData {
    private Post post;
    private Long[] usersTaggedIds;
    private RelationshipStatus relationshipStatus;
}
