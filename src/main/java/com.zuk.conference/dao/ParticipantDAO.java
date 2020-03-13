package com.zuk.conference.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuk.conference.conection.ConnectionManager;
import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;
import java.io.FileNotFoundException;
import java.sql.Connection;

public abstract class  ParticipantDAO {//TODO make abstract class

    protected Connection con;
    protected ConnectionManager cm;
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected String jsonInString = "";

    public ParticipantDAO() {
        cm = new ConnectionManager();
        con = cm.getConnection();
    }


    public abstract Participant findById(int id);
    public abstract void updateIdConference(String idConference, int id);

    public abstract String insertParticipant(Participant participant) throws FileNotFoundException;
    public abstract String getParticipant(Participant participant);
    public abstract String login(Participant participant);
    public abstract Boolean isadmin(Participant admin);
    public abstract Boolean removeFromConference(Participant participant,int idConference);
}
