package com.zuk.conference.model;


import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "room")
@EntityListeners(AuditingEntityListener.class)
@Data
public class RoomJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "firstFloorCapacity")
    private int firstFloorCapacity;

    @Column(name = "secondFloorCapacity")
    private int secondFloorCapacity;
}
