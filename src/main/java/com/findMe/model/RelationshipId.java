package com.findMe.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class RelationshipId implements Serializable {

    @Column(name = "USER_FROM_ID")
    private Long userFromId;

    @Column(name = "USER_TO_ID")
    private Long userToId;
}
