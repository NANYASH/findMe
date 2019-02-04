package com.findMe.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "POST")
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Post {

    @SequenceGenerator(name = "POST_SEQ", sequenceName = "POST_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ")
    @Id
    @Column(name = "ID")
    private Long id;


    @Column(name = "TEXT", length = 200)
    //200 symbols max
    private String text;

    @Column(name = "DATE_POSTED")
    private LocalDate datePosted;

    //no validation
    private String location;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "USER_POSTED_ID")
    private User userPosted;
    //TODO
    // levels permissions

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "USER_PAGE_ID")
    private User userPagePosted;

    //TODO
    //comments

    @OneToMany(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "USER_TAGGED_ID")
    List<User> usersTagged;
}
