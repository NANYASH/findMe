package com.findMe.model;

import com.findMe.model.enums.RelationshipStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "RELATIONSHIP")
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class Relationship implements Serializable{

    @EmbeddedId
    private RelationshipId relationshipId;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private RelationshipStatus relationshipStatus;

    @Column(name = "LAST_UPDATE_DATE")
    private LocalDate lastUpdateDate;

}
