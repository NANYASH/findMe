package com.findMe.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "MESSAGE")
@EqualsAndHashCode(exclude = {"dateRead"})
@ToString
@Getter
@Setter
public class Message {

    @SequenceGenerator(name = "MESSAGE_SEQ", sequenceName = "MESSAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_SEQ")
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "TEXT", length = 140)
    private String text;

    @Column(name = "DATE_SENT")
    private LocalDate dateSent;

    @Column(name = "DATE_EDITED")
    private LocalDate dateEdited;

    @Column(name = "DATE_DELETED")
    private LocalDate dateDeleted;

    @Column(name = "DATE_READ")
    private LocalDate dateRead;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "USER_FROM_ID")
    private User userFrom;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "USER_TO_ID")
    private User userTo;
}
