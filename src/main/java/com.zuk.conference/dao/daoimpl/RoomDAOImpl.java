package com.zuk.conference.dao.daoimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuk.conference.conection.ConnectionManager;
import com.zuk.conference.dao.RoomDAO;
import com.zuk.conference.model.Room;
import org.springframework.util.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDAOImpl implements RoomDAO {
    private ObjectMapper objectMapper = new ObjectMapper();

    ConnectionManager cm = new ConnectionManager();
    Connection con = cm.getConnection();
    String jsonInString="";

    @Override
    public void insertRoom() {

    }

    @Override
    public Room getRoomFromId(int id) {
        Room room = new Room();
        room.setId(id);
        if (con != null) {
            try {
                PreparedStatement pr,pr1;

                pr1 = con.prepareStatement("SELECT  * from ROOM where ID=?");
                pr1.setInt(1,room.getId());

                ResultSet resultSet = pr1.executeQuery();

                if(resultSet.next()) {
                    room.setName(resultSet.getString("NAME"));
                    room.setFirstFloorCapacity(resultSet.getInt("FIRSTFLOORCAPACITY"));
                    room.setSecondFloorCapacity(resultSet.getInt("SECONDFLOORCAPACITY"));
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        return room;
    }

    @Override
    public Room getRoom() {
        return null;
    }
}
