package com.findMe.validator;


import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor(force = true)
public class RequestData {
    @Getter
    @Setter
    private Relationship relationship;
    @Getter
    @Setter
    private RelationshipStatus newStatus;
    @Getter
    @Setter
    private Long numberOfFriends;
    @Getter
    @Setter
    private Long numberOfOutgoingRequests;
}
