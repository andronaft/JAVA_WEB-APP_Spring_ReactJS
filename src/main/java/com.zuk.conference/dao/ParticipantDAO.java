package com.zuk.conference.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuk.conference.conection.ConnectionManager;
import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class  ParticipantDAO {

    protected Connection con;
    protected ConnectionManager cm;
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected String jsonInString = "";

    public ParticipantDAO() {
        cm = new ConnectionManager();
        con = cm.getConnection();
    }

    protected abstract Participant findById(int id);
    protected abstract Participant findByLogin(String login);
    protected abstract ArrayList findAll();
    protected abstract boolean update(Participant participant);
    protected abstract boolean updateIdConference(String idConference, int id);
    protected abstract boolean save(Participant participant);
    protected abstract boolean isAdmin(Participant admin);

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
