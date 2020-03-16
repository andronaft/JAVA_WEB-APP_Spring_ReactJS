package com.zuk.conference.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuk.conference.conection.ConnectionManager;
import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class  ConferenceDAO {

    protected Connection con;
    protected ConnectionManager cm;
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected String jsonInString = "";

    public ConferenceDAO() {
        cm = new ConnectionManager();
        con = cm.getConnection();
    }

    protected abstract Conference findById(int id);
    protected abstract ArrayList findAll();
    protected abstract boolean save(Conference conference);
    protected abstract boolean update(Conference conference);
    protected abstract boolean updateIdParticipant(String idParticipant, int amountParticipant, int id);
    protected abstract boolean updateDateAndTime(Conference conference);
    protected abstract boolean delete(int conferenceId);


    protected PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

}
