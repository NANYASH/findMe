package com.findMe.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MESSAGE")
@EqualsAndHashCode(exclude = {"dateRead"})
@ToString
public class Message {

    @SequenceGenerator(name = "MESSAGE_SEQ", sequenceName = "MESSAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_SEQ")
    @Id
    @Column(name = "ID")
    @Getter
    @Setter
    private Long id;

    @Column(name = "TEXT")
    @Getter
    @Setter
    private String text;

    @Column(name = "DATE_SENT")
    @Getter
    @Setter
    private Date dateSent;

    @Column(name = "DATE_READ")
    @Getter
    @Setter
    private Date dateRead;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "ID_USER_FROM")
    @Getter
    @Setter
    private User userFrom;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "ID_USER_TO")
    @Getter
    @Setter
    private User userTo;
}
