package com.zuk.conference.model;


import java.sql.Date;
import java.sql.Time;

public class Conference {
    private int id;
    private String name;
    private int id_room;
    private String name_room;
    private int capacity_room;
    private int amount_participant;
    private Date datee;
    private Time timee;
    private String id_participant;

    public Conference(String name, int id_room, Date datee, Time timee) {
        this.name = name;
        this.id_room = id_room;
        this.datee = datee;
        this.timee = timee;
    }

    public Conference() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_room() {
        return id_room;
    }

    public void setId_room(int id_room) {
        this.id_room = id_room;
    }

    public String getName_room() {
        return name_room;
    }

    public void setName_room(String name_room) {
        this.name_room = name_room;
    }

    public int getCapacity_room() {
        return capacity_room;
    }

    public void setCapacity_room(int capacity_room) {
        this.capacity_room = capacity_room;
    }

    public int getAmount_participant() {
        return amount_participant;
    }

    public void setAmount_participant(int amount_participant) {
        this.amount_participant = amount_participant;
    }

    public Date getDatee() {
        return datee;
    }

    public void setDatee(Date datee) {
        this.datee = datee;
    }

    public Time getTimee() {
        return timee;
    }

    public void setTimee(Time timee) {
        this.timee = timee;
    }

    public String getId_participant() {
        return id_participant;
    }

    public void setId_participant(String id_participant) {
        this.id_participant = id_participant;
    }
}
