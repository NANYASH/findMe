package com.findMe.model.validateData;

import com.findMe.model.Post;
import com.findMe.model.Relationship;
import com.findMe.model.User;
import com.findMe.model.enums.RelationshipStatus;
import javafx.geometry.Pos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class PostValidatorRequestData {
    private Post post;
    private Long[] usersTaggedIds;
    private RelationshipStatus relationshipStatus;
}
