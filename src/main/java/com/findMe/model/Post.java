package com.findMe.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static com.findMe.util.Util.createUsersTaggedNamesString;

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

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "USER_PAGE_ID")
    private User userPagePosted;

    //TODO comments

    @ManyToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinTable(name = "USERS_TAGGED",
            joinColumns = @JoinColumn(name = "POST_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private List<User> usersTagged;


    //TODO Change tagged users names output approach.
    public String getUsersTaggedNames() {
        return createUsersTaggedNamesString(usersTagged);
    }
}
