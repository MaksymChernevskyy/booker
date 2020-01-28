package com.be.booker.business.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@Getter
@Setter
@EqualsAndHashCode
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "bookedFrom")
    private LocalDateTime bookedFrom;

    @Column(name = "bookedTo")
    private LocalDateTime bookedTo;

    @Column(name = "userlogin")
    private String userLogin;

    @Column(name = "roomName")
    private String roomName;

}
