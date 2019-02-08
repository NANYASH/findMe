package com.findMe.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
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
    private String text;

    @Column(name = "DATE_POSTED")
    private LocalDate datePosted;

    @Column(name = "LOCATION")
    private String location;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "USER_POSTED_ID")
    private User userPosted;
    //TODO
    // levels permissions

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "USER_PAGE_ID")
    private User userPagePosted;

    //TODO
    //comments

    @ManyToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinTable(name = "USERS_TAGGED",
            joinColumns = @JoinColumn(name = "POST_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private List<User> usersTagged;
}
