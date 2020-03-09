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

    public String getName() {
        return name;
    }

    public int getId_room() {
        return id_room;
    }

    public String getName_room() {
        return name_room;
    }

    public int getCapacity_room() {
        return capacity_room;
    }

    public int getAmount_participant() {
        return amount_participant;
    }

    public Date getDatee() {
        return datee;
    }

    public Time getTimee() {
        return timee;
    }

    public String getId_participant() {
        return id_participant;
    }


    public static Builder newBuilder() {
        return new Conference().new Builder();
    }

    public class Builder {

        private Builder() {}

        public Builder setId(int id) {
            Conference.this.id = id;
            return  this;
        }

        public Builder  setName(String name) {
            Conference.this.name = name;
            return this;
        }

        public Builder setId_room(int id_room) {
            Conference.this.id_room = id_room;
            return this;
        }

        public Builder setName_room(String name_room) {
            Conference.this.name_room = name_room;
            return this;
        }

        public Builder setCapacity_room(int capacity_room) {
            Conference.this.capacity_room = capacity_room;
            return this;
        }

        public Builder setAmount_participant(int amount_participant) {
            Conference.this.amount_participant = amount_participant;
            return this;

        }
        public Builder setDatee(Date datee) {
            Conference.this.datee = datee;
            return this;
        }

        public Builder setTimee(Time timee) {
            Conference.this.timee = timee;
            return this;
        }
        public Builder setId_participant(String id_participant) {
            Conference.this.id_participant = id_participant;
            return this;
        }


        public Conference build() {
            return Conference.this;
        }
    }
}
