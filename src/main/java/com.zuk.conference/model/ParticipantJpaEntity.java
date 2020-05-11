package com.zuk.conference.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "participant")
@EntityListeners(AuditingEntityListener.class)
@Data
public class ParticipantJpaEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "birthday")
    private Date birthDay;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "id_conference_participant")
    private String id_conference_participant;

}
