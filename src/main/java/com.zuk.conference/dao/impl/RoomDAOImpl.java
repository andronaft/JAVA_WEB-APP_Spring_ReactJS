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
    protected boolean save(Room room) {
        if (con != null) {
            try {
                PreparedStatement pr = getPrepareStatement("insert into ROOM (NAME,FIRSTFLOORCAPACITY,SECONDFLOORCAPACITY) values (?,?,?)");
                pr.setString(1,room.getName());
                pr.setInt(2,room.getFirstFloorCapacity());
                pr.setInt(3,room.getSecondFloorCapacity());
                pr.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
