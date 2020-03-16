package com.zuk.conference.dao.impl;

import com.zuk.conference.dao.RoomDAO;
import com.zuk.conference.model.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDAOImpl extends RoomDAO {

    @Override
    public Room findById(int id) {
        Room room = null;
        if (con != null) {
            try {
                PreparedStatement pr = getPrepareStatement("SELECT  * from ROOM where ID=?");
                pr.setInt(1,id);
                ResultSet resultSet = pr.executeQuery();
                if(resultSet.next()) {
                    room = Room.newBuilder()
                            .setId(id)
                            .setName(resultSet.getString("NAME"))
                            .setFirstFloorCapacity(resultSet.getInt("FIRSTFLOORCAPACITY"))
                            .setSecondFloorCapacity(resultSet.getInt("SECONDFLOORCAPACITY"))
                            .build();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return room;
    }

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
