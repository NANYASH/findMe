package com.findMe.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "USER_TABLE")
@EqualsAndHashCode(exclude = {"dateLastActive","messagesSent","messagesReceived"})
@ToString(exclude = {"messagesSent","messagesReceived"})
@Getter
@Setter
public class User {

    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    //TODO from existed data
    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "DATE_REGISTERED")
    private LocalDate dateRegistered;

    @Column(name = "DATE_LAST_ACTIVE")
    private LocalDate dateLastActive;

    //TODO enum
    @Column(name = "RELATIONSHIPS_STATUS")
    private String relationshipsStatus;

    @Column(name = "RELIGION")
    private String religion;

    //TODO from existed data
    @Column(name = "SCHOOL")
    private String school;

    @Column(name = "UNIVERSITY")
    private String university;

    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(targetEntity = Message.class, fetch = FetchType.LAZY, mappedBy = "userFrom")
    private List<Message> messagesSent;

    @OneToMany(targetEntity = Message.class, fetch = FetchType.LAZY, mappedBy = "userTo")
    private List<Message> messagesReceived;

    //interests;
}
