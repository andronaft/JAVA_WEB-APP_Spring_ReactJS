package com.zuk.conference.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuk.conference.conection.ConnectionManager;
import com.zuk.conference.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class RoomDAO {
    protected Connection con;
    protected ConnectionManager cm;


    public RoomDAO() {
        cm = new ConnectionManager();
        con = cm.getConnection();
    }

    protected abstract Room findById(int id);
    protected abstract boolean save(Room room);

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
