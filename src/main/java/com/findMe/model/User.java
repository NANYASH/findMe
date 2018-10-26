package com.findMe.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "USER_TABLE")
@EqualsAndHashCode(exclude = {"dateLastActive","messagesSent","messagesReceived"})
@ToString(exclude = {"messagesSent","messagesReceived"})
public class User {

    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @Id
    @Column(name = "ID")
    @Getter
    @Setter
    private Long id;

    @Column(name = "FIRST_NAME")
    @Getter
    @Setter
    private String firstName;

    @Column(name = "LAST_NAME")
    @Getter
    @Setter
    private String lastName;

    @Column(name = "PHONE")
    @Getter
    @Setter
    private String phone;

    //TODO from existed data
    @Column(name = "COUNTRY")
    @Getter
    @Setter
    private String country;

    @Column(name = "CITY")
    @Getter
    @Setter
    private String city;

    @Column(name = "AGE")
    @Getter
    @Setter
    private Integer age;

    @Column(name = "DATE_REGISTERED")
    @Getter
    @Setter
    private Date dateRegistered;

    @Column(name = "DATE_LAST_ACTIVE")
    @Getter
    @Setter
    private Date dateLastActive;

    //TODO enum
    @Column(name = "RELATIONSHIPS_STATUS")
    @Getter
    @Setter
    private String relationshipsStatus;

    @Column(name = "RELIGION")
    @Getter
    @Setter
    private String religion;

    //TODO from existed data
    @Column(name = "SCHOOL")
    @Getter
    @Setter
    private String school;

    @Column(name = "UNIVERSITY")
    @Getter
    @Setter
    private String university;

    @OneToMany(targetEntity = Message.class, fetch = FetchType.LAZY, mappedBy = "userFrom")
    @Getter
    @Setter
    private List<Message> messagesSent;

    @OneToMany(targetEntity = Message.class, fetch = FetchType.LAZY, mappedBy = "userTo")
    @Getter
    @Setter
    private List<Message> messagesReceived;

    //interests;
}
