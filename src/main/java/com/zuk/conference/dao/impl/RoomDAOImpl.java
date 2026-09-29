package com.zuk.conference.dao.impl;

import com.zuk.conference.dao.RoomDAO;
import com.zuk.conference.model.Room;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomDAOImpl implements RoomDAO {

    private static final RowMapper<Room> ROW_MAPPER = (rs, rowNum) -> Room.newBuilder()
            .setId(rs.getInt("ID"))
            .setName(rs.getString("NAME"))
            .setFirstFloorCapacity(rs.getInt("FIRSTFLOORCAPACITY"))
            .setSecondFloorCapacity(rs.getInt("SECONDFLOORCAPACITY"))
            .build();

    private final JdbcTemplate jdbcTemplate;

    public RoomDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Room findById(int id) {
        List<Room> rooms = jdbcTemplate.query("SELECT * FROM ROOM WHERE ID = ?", ROW_MAPPER, id);
        return rooms.isEmpty() ? null : rooms.get(0);
    }
}
