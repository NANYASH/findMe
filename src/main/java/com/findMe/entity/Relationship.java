package com.findMe.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "RELATIONSHIP")
@EqualsAndHashCode
@ToString
@NoArgsConstructor(force = true)
public class Relationship {

    @SequenceGenerator(name = "RS_SEQ", sequenceName = "RS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RS_SEQ")
    @Id
    @Column(name = "ID")
    @Getter
    @Setter
    private Long id;

    @Column(name = "USER_FROM_ID")
    @Getter @Setter
    Long userFromId;

    @Column(name = "USER_TO_ID")
    @Getter @Setter
    Long userToId;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    @Getter @Setter
    RelationshipStatus relationshipStatus;

    public Relationship(Long userFromId, Long userToId, RelationshipStatus relationshipStatus) {
        this.userFromId = userFromId;
        this.userToId = userToId;
        this.relationshipStatus = relationshipStatus;
    }
}
