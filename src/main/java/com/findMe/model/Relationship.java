package com.findMe.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "RELATIONSHIP")
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Relationship implements Serializable{

    @EmbeddedId
    @Getter
    @Setter
    private RelationshipId relationshipId;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    @Getter
    @Setter
    private RelationshipStatus relationshipStatus;

}
