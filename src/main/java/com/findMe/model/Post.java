package com.findMe.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "POST")
@EqualsAndHashCode
@ToString
public class Post {

    @SequenceGenerator(name = "POST_SEQ", sequenceName = "POST_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ")
    @Id
    @Column(name = "ID")
    @Getter
    @Setter
    private Long id;

    @Column(name = "TEXT")
    @Getter
    @Setter
    private String text;

    @Column(name = "DATE_POSTED")
    @Getter
    @Setter
    private Date datePosted;

    @Column(name = "ID_USER")
    @Getter
    @Setter
    private User userPosted;
    //TODO
    // levels permissions

    //TODO
    //COMMENTS
}
