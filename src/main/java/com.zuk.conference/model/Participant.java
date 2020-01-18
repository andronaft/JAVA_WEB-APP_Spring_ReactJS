package com.zuk.conference.model;

import java.sql.Date;

public class Participant {
    private int id;
    private String firstName;
    private String lastName;
    private Date birthDay;
    private String login;
    private String password;
    private String role;
    private String id_conference_participant;

    public Participant(String firstName, String lastName, Date birthDay, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.login = login;
        this.password = password;
    }

    public Participant(int id, String firstName, String lastName, Date birthDay, String login, String password,String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Participant() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId_conference_participant() {
        return id_conference_participant;
    }

    public void setId_conference_participant(String id_conference_participant) {
        this.id_conference_participant = id_conference_participant;
    }
}
