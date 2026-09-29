package com.zuk.conference.dao.impl;

import com.zuk.conference.dao.ParticipantDAO;
import com.zuk.conference.model.Participant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class ParticipantDAOImpl implements ParticipantDAO {

    private static final RowMapper<Participant> ROW_MAPPER = (rs, rowNum) -> Participant.newBuilder()
            .setId(rs.getInt("ID"))
            .setFirstName(rs.getString("FIRSTNAME"))
            .setLastName(rs.getString("LASTNAME"))
            .setLogin(rs.getString("LOGIN"))
            .setPassword(rs.getString("PASSWORD"))
            .setBirthDay(rs.getObject("BIRTHDAY", LocalDate.class))
            .setId_conference_participant(rs.getString("ID_CONFERENCE_PARTICIPANT"))
            .setRole(rs.getString("ROLE"))
            .build();

    private final JdbcTemplate jdbcTemplate;

    public ParticipantDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Participant findById(int id) {
        return first(jdbcTemplate.query("SELECT * FROM PARTICIPANT WHERE ID = ?", ROW_MAPPER, id));
    }

    @Override
    public Participant findByIdForUpdate(int id) {
        return first(jdbcTemplate.query("SELECT * FROM PARTICIPANT WHERE ID = ? FOR UPDATE", ROW_MAPPER, id));
    }

    @Override
    public Participant findByLogin(String login) {
        return first(jdbcTemplate.query("SELECT * FROM PARTICIPANT WHERE LOGIN = ?", ROW_MAPPER, login));
    }

    @Override
    public int save(Participant participant) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO PARTICIPANT (FIRSTNAME, LASTNAME, LOGIN, PASSWORD, ROLE, BIRTHDAY) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, participant.getFirstName());
            ps.setString(2, participant.getLastName());
            ps.setString(3, participant.getLogin());
            ps.setString(4, participant.getPassword());
            ps.setString(5, participant.getRole());
            ps.setObject(6, participant.getBirthDay());
            return ps;
        }, keyHolder);
        // H2 returns only the identity column, PostgreSQL returns the whole row;
        // the key map is case-insensitive, so "id" works for both.
        Map<String, Object> keys = keyHolder.getKeys();
        return ((Number) keys.get("id")).intValue();
    }

    @Override
    public boolean updateIdConference(String idConference, int id) {
        return jdbcTemplate.update(
                "UPDATE PARTICIPANT SET ID_CONFERENCE_PARTICIPANT = ? WHERE ID = ?", idConference, id) > 0;
    }

    @Override
    public boolean updatePassword(int id, String passwordHash) {
        return jdbcTemplate.update("UPDATE PARTICIPANT SET PASSWORD = ? WHERE ID = ?", passwordHash, id) > 0;
    }

    private static Participant first(List<Participant> participants) {
        return participants.isEmpty() ? null : participants.get(0);
    }
}
