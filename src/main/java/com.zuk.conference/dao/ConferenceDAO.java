package com.zuk.conference.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuk.conference.conection.ConnectionManager;
import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class  ConferenceDAO {

    protected Connection con;
    protected ConnectionManager cm;
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected String jsonInString = "";

    public ConferenceDAO() {
        cm = new ConnectionManager();
        con = cm.getConnection();
    }

    public abstract String joinNewParticipant(Participant participant, Conference conference);
    public abstract String cancelConferece(Participant admin,Conference conference);
    public abstract String getAllConference();
    public abstract String createConf(Participant participant,Conference conference);
    public abstract String removeParticipant(Participant admin,Participant participant,Conference conference);
    public abstract String changeTime(Participant participant, Conference conference);

    public PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

}
