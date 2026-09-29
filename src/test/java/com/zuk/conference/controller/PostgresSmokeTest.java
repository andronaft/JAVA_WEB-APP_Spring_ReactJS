package com.zuk.conference.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Runs the main flow against a real PostgreSQL database with the "postgres" profile.
 * Only enabled when DB_URL points to PostgreSQL (the CI workflow starts one).
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("postgres")
@EnabledIfEnvironmentVariable(named = "DB_URL", matches = "jdbc:postgresql:.*")
@Transactional
class PostgresSmokeTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void mainFlowWorksOnPostgres() throws Exception {
        jdbcTemplate.update("INSERT INTO ROOM (NAME, FIRSTFLOORCAPACITY, SECONDFLOORCAPACITY) VALUES ('Smoke room', 1, 1)");
        int roomId = jdbcTemplate.queryForObject("SELECT MAX(ID) FROM ROOM", Integer.class);

        MvcResult registered = mvc.perform(post("/register")
                        .param("firstname", "Smoke").param("lastname", "Test").param("birthday", "2000-01-31")
                        .param("login", "smoke-admin").param("password", "smoke"))
                .andExpect(jsonPath("$.login").value("smoke-admin"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andReturn();
        MockHttpSession session = (MockHttpSession) registered.getRequest().getSession(false);
        int adminId = jdbcTemplate.queryForObject("SELECT ID FROM PARTICIPANT WHERE LOGIN = 'smoke-admin'", Integer.class);
        jdbcTemplate.update("UPDATE PARTICIPANT SET ROLE = 'admin' WHERE ID = ?", adminId);

        mvc.perform(post("/createconf")
                        .param("name", "Smoke conference").param("id_room", String.valueOf(roomId))
                        .param("datee", LocalDate.now().plusDays(1).toString()).param("timee", "10:00")
                        .param("admin_id", String.valueOf(adminId)).param("admin_password", "smoke"))
                .andExpect(jsonPath("$[0]").value("Conference was created"));
        int conferenceId = jdbcTemplate.queryForObject(
                "SELECT ID FROM CONFERENCE WHERE NAME = 'Smoke conference'", Integer.class);

        mvc.perform(post("/joinConference").param("conference_id", String.valueOf(conferenceId)).session(session))
                .andExpect(jsonPath("$[0]").value("You joined the conference \"Smoke conference\""));
        mvc.perform(get("/getAccount").session(session))
                .andExpect(jsonPath("$.id_conference_participant").value(conferenceId + ","));

        mvc.perform(post("/cancelConf")
                        .param("conference_id", String.valueOf(conferenceId))
                        .param("admin_id", String.valueOf(adminId)).param("admin_password", "smoke"))
                .andExpect(jsonPath("$[0]").value("Conference was cancelled"));
        mvc.perform(get("/getAccount").session(session))
                .andExpect(jsonPath("$.id_conference_participant").value(""));
    }
}
