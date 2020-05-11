package com.zuk.conference.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;


@Entity
@Table(name = "conference")
@EntityListeners(AuditingEntityListener.class)
@Data
public class ConferenceSpringEntity {

    private int id;
    private String name;
    private int id_room;
    private String name_room;
    private int capacity_room;
    private int amount_participant;
    private Date datee;
    private Time timee;
    private String id_participant;
}
