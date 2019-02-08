package com.findMe.model.validateData;


import com.findMe.model.Relationship;
import com.findMe.model.enums.RelationshipStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class RelationshipValidatorRequestData {
    private Long userFromId;
    private Long userToId;
    private Relationship relationship;
    private RelationshipStatus newStatus;
    private Long numberOfFriends;
    private Long numberOfOutgoingRequests;
}
