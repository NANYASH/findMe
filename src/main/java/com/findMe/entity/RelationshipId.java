package com.findMe.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@ToString
@NoArgsConstructor(force = true)
public class RelationshipId implements Serializable{
    @Column(name = "USER_FROM_ID")
    @Getter
    @Setter
    private Long userFromId;

    @Column(name = "USER_TO_ID")
    @Getter
    @Setter
    private Long userToId;

    public RelationshipId(Long userFromId, Long userToId) {
        this.userFromId = userFromId;
        this.userToId = userToId;
    }

}
