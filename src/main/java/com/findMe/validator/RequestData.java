package com.findMe.validator;


import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class RequestData {
    private Relationship relationship;
    private RelationshipStatus newStatus;
    private Long numberOfFriends;
    private Long numberOfOutgoingRequests;
}
