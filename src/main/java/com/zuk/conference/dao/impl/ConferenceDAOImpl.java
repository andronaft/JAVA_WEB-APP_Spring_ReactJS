package com.zuk.conference.dao.impl;

import com.zuk.conference.dao.ConferenceDAO;
import com.zuk.conference.model.Conference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ConferenceDAOImpl implements ConferenceDAO {

    private static final RowMapper<Conference> ROW_MAPPER = (rs, rowNum) -> Conference.newBuilder()
            .setId(rs.getInt("ID"))
            .setName(rs.getString("NAME"))
            .setId_room(rs.getInt("ID_ROOM"))
            .setName_room(rs.getString("NAME_ROOM"))
            .setId_participant(rs.getString("ID_PARTICIPANT"))
            .setCapacity_room(rs.getInt("CAPACITY_ROOM"))
            .setAmount_participant(rs.getInt("AMOUNT_PARTICIPANT"))
            .setDatee(rs.getObject("DATEE", LocalDate.class))
            .setTimee(rs.getObject("TIMEE", LocalTime.class))
            .build();

    private final JdbcTemplate jdbcTemplate;

    public ConferenceDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Conference findById(int id) {
        return first(jdbcTemplate.query("SELECT * FROM CONFERENCE WHERE ID = ?", ROW_MAPPER, id));
    }

    @Override
    public Conference findByIdForUpdate(int id) {
        return first(jdbcTemplate.query("SELECT * FROM CONFERENCE WHERE ID = ? FOR UPDATE", ROW_MAPPER, id));
    }

    @Override
    public List<Conference> findUpcoming() {
        return jdbcTemplate.query(
                "SELECT * FROM CONFERENCE WHERE DATEE >= CURRENT_DATE ORDER BY DATEE ASC, TIMEE ASC, ID ASC",
                ROW_MAPPER);
    }

    @Override
    public void save(Conference conference) {
        jdbcTemplate.update(
                "INSERT INTO CONFERENCE (NAME, ID_ROOM, NAME_ROOM, CAPACITY_ROOM, AMOUNT_PARTICIPANT, DATEE, TIMEE) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                conference.getName(),
                conference.getId_room(),
                conference.getName_room(),
                conference.getCapacity_room(),
                conference.getAmount_participant(),
                conference.getDatee(),
                conference.getTimee());
    }

    @Override
    public boolean updateIdParticipant(String idParticipant, int amountParticipant, int id) {
        return jdbcTemplate.update(
                "UPDATE CONFERENCE SET ID_PARTICIPANT = ?, AMOUNT_PARTICIPANT = ? WHERE ID = ?",
                idParticipant, amountParticipant, id) > 0;
    }

    @Override
    public boolean updateDateAndTime(int id, LocalDate datee, LocalTime timee) {
        return jdbcTemplate.update(
                "UPDATE CONFERENCE SET DATEE = ?, TIMEE = ? WHERE ID = ?",
                datee, timee, id) > 0;
    }

    @Override
    public boolean delete(int conferenceId) {
        return jdbcTemplate.update("DELETE FROM CONFERENCE WHERE ID = ?", conferenceId) > 0;
    }

    private static Conference first(List<Conference> conferences) {
        return conferences.isEmpty() ? null : conferences.get(0);
    }
}
