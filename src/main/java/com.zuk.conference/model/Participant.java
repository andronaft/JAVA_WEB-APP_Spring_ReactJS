package com.zuk.conference.model;



import java.sql.Date;
import java.util.Objects;

public class Participant {
    private int id;
    private String firstName;
    private String lastName;
    private Date birthDay;
    private String login;
    private String password;
    private String role;
    private String id_conference_participant;


    public Participant() {
    }

    public Participant(String firstname, String lastname, Date birthday, String login, String password) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getId_conference_participant() {
        return id_conference_participant;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public static Participant.Builder newBuilder() {
        return new Participant().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(int id) {
            Participant.this.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            Participant.this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            Participant.this.lastName = lastName;
            return this;
        }

        public Builder setBirthDay(Date birthDay) {
            Participant.this.birthDay = birthDay;
            return this;
        }

        public Builder setLogin(String login) {
            Participant.this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            Participant.this.password = password;
            return this;
        }

        public Builder setRole(String role) {
            Participant.this.role = role;
            return this;
        }

        public Builder setId_conference_participant(String id_conference_participant) {
            Participant.this.id_conference_participant = id_conference_participant;
            return this;
        }


        public Participant build() {
            return Participant.this;
        }

    }

     @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Participant that = (Participant) o;
            return id == that.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
}
