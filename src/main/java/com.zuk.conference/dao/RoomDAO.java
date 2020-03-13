package com.zuk.conference.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuk.conference.conection.ConnectionManager;
import com.zuk.conference.model.Room;

import java.sql.Connection;

public abstract class RoomDAO {//TODO make abstract class
    protected Connection con;
    protected ConnectionManager cm;
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected String jsonInString = "";

    public RoomDAO() {
        cm = new ConnectionManager();
        con = cm.getConnection();
    }


    public abstract void insertRoom();
    public abstract Room getRoomFromId(int id);
    public abstract Room getRoom();
}
