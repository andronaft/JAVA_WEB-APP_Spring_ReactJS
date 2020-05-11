package com.zuk.conference.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;


@Entity
@Table(name = "conference")
@EntityListeners(AuditingEntityListener.class)
@Data
public class ConferenceJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "id_room")
    private int id_room;

    @Column(name = "name_room")
    private String name_room;

    @Column(name = "capacity_room")
    private int capacity_room;

    @Column(name = "amount_participant")
    private int amount_participant;

    @Column(name = "datee")
    private Date datee;

    @Column(name = "timee")
    private Time timee;

    @Column(name = "id_participant")
    private String id_participant;
}
