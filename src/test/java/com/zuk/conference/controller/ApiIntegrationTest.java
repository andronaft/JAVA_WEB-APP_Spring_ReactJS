package com.zuk.conference.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * End-to-end tests of the HTTP API against the in-memory H2 database with the demo data
 * from data-h2.sql. Every test runs in a transaction that is rolled back afterwards.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ApiIntegrationTest {

    private static final int ADMIN_ID = 1;
    private static final int SMALL_CONFERENCE_ID = 4; // room capacity 2

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ---------- conferences ----------

    @Test
    void listsUpcomingConferences() throws Exception {
        mvc.perform(get("/getAllConference"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(4))
                .andExpect(jsonPath("$.arrayList[0].name").value("literary evening"))
                .andExpect(jsonPath("$.arrayList[0].datee").value(LocalDate.now().plusDays(7).toString()))
                .andExpect(jsonPath("$.arrayList[0].timee").value("11:00:00"));
    }

    @Test
    void pastConferencesAreHidden() throws Exception {
        jdbcTemplate.update("UPDATE CONFERENCE SET DATEE = ? WHERE ID = 1", LocalDate.now().minusDays(1));
        jdbcTemplate.update("UPDATE CONFERENCE SET DATEE = ? WHERE ID = 2", LocalDate.now());

        mvc.perform(get("/getAllConference"))
                .andExpect(jsonPath("$.amount").value(3))
                .andExpect(jsonPath("$.arrayList[0].id").value(2));
    }

    // ---------- registration / login ----------

    @Test
    void registerReturnsSavedUserWithIdAndWithoutPassword() throws Exception {
        MockHttpSession session = register("newbie", "pass");

        mvc.perform(get("/getAccount").session(session))
                .andExpect(jsonPath("$.login").value("newbie"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("First"))
                .andExpect(jsonPath("$.birthDay").value("2000-01-31"))
                .andExpect(jsonPath("$.password").doesNotExist());

        String hash = jdbcTemplate.queryForObject(
                "SELECT PASSWORD FROM PARTICIPANT WHERE LOGIN = 'newbie'", String.class);
        assertThat(hash).startsWith("$2");
    }

    @Test
    void registerRejectsTakenLogin() throws Exception {
        mvc.perform(post("/register")
                        .param("firstname", "A").param("lastname", "B").param("birthday", "2000-01-01")
                        .param("login", "demo").param("password", "x"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("This login is not available"));
    }

    @Test
    void registerRejectsBlankFields() throws Exception {
        mvc.perform(post("/register")
                        .param("firstname", " ").param("lastname", "B").param("birthday", "2000-01-01")
                        .param("login", "someone").param("password", "x"))
                .andExpect(jsonPath("$[0]").value("All fields are required"));
    }

    @Test
    void loginReturnsUserWithoutPassword() throws Exception {
        mvc.perform(post("/login").param("login", "admin").param("password", "admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ADMIN_ID))
                .andExpect(jsonPath("$.role").value("admin"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void loginWithWrongPasswordFails() throws Exception {
        mvc.perform(post("/login").param("login", "admin").param("password", "nope"))
                .andExpect(jsonPath("$[0]").value("Incorrect login or password"));
        mvc.perform(post("/login").param("login", "nobody").param("password", "nope"))
                .andExpect(jsonPath("$[0]").value("Incorrect login or password"));
    }

    @Test
    void legacyMd5PasswordIsUpgradedOnLogin() throws Exception {
        jdbcTemplate.update("INSERT INTO PARTICIPANT (FIRSTNAME, LASTNAME, LOGIN, PASSWORD, BIRTHDAY) "
                + "VALUES ('Old', 'User', 'old', '21232f297a57a5a743894a0e4a801fc3', DATE '1999-01-01')");

        mvc.perform(post("/login").param("login", "old").param("password", "admin"))
                .andExpect(jsonPath("$.login").value("old"));

        String hash = jdbcTemplate.queryForObject("SELECT PASSWORD FROM PARTICIPANT WHERE LOGIN = 'old'", String.class);
        assertThat(hash).startsWith("$2");
        mvc.perform(post("/login").param("login", "old").param("password", "admin"))
                .andExpect(jsonPath("$.login").value("old"));
    }

    @Test
    void accountRequiresLoginAndIgnoresIdParameter() throws Exception {
        mvc.perform(get("/getAccount").param("id", String.valueOf(ADMIN_ID)))
                .andExpect(jsonPath("$[0]").value("You must log in first"));

        MockHttpSession demo = login("demo", "demo");
        mvc.perform(get("/getAccount").param("id", String.valueOf(ADMIN_ID)).session(demo))
                .andExpect(jsonPath("$.login").value("demo"));
    }

    @Test
    void logoutEndsTheSession() throws Exception {
        MockHttpSession demo = login("demo", "demo");
        mvc.perform(post("/logout").session(demo)).andExpect(jsonPath("$[0]").value("You have logged out"));
        mvc.perform(get("/getAccount").session(demo)).andExpect(jsonPath("$[0]").value("You must log in first"));
    }

    // ---------- joining ----------

    @Test
    void joinConference() throws Exception {
        MockHttpSession demo = login("demo", "demo");

        mvc.perform(post("/joinConference").param("conference_id", "1").session(demo))
                .andExpect(jsonPath("$[0]").value("You joined the conference \"literary evening\""));

        mvc.perform(get("/getAllConference"))
                .andExpect(jsonPath("$.arrayList[0].amount_participant").value(1))
                .andExpect(jsonPath("$.arrayList[0].id_participant").value("2,"));
        mvc.perform(get("/getAccount").session(demo))
                .andExpect(jsonPath("$.id_conference_participant").value("1,"));

        mvc.perform(post("/joinConference").param("conference_id", "1").session(demo))
                .andExpect(jsonPath("$[0]").value("You have already joined this conference"));
    }

    @Test
    void joinRequiresLogin() throws Exception {
        mvc.perform(post("/joinConference").param("conference_id", "1").param("user_id", "2"))
                .andExpect(jsonPath("$[0]").value("You must log in first"));
    }

    @Test
    void cannotJoinFullConference() throws Exception {
        for (String login : new String[]{"u1", "u2"}) {
            mvc.perform(post("/joinConference").param("conference_id", String.valueOf(SMALL_CONFERENCE_ID))
                            .session(register(login, "pw")))
                    .andExpect(jsonPath("$[0]", startsWith("You joined")));
        }

        mvc.perform(post("/joinConference").param("conference_id", String.valueOf(SMALL_CONFERENCE_ID))
                        .session(register("u3", "pw")))
                .andExpect(jsonPath("$[0]").value("Sorry, the conference is full"));
    }

    @Test
    void cannotJoinMissingConference() throws Exception {
        mvc.perform(post("/joinConference").param("conference_id", "999").session(login("demo", "demo")))
                .andExpect(jsonPath("$[0]").value("Conference not found"));
    }

    // ---------- admin ----------

    @Test
    void createConferenceStoresRoomData() throws Exception {
        String date = LocalDate.now().plusDays(3).toString();
        mvc.perform(post("/createconf")
                        .param("name", "New & shiny").param("id_room", "2")
                        .param("datee", date).param("timee", "09:30:00")
                        .param("admin_id", String.valueOf(ADMIN_ID)).param("admin_password", "admin"))
                .andExpect(jsonPath("$[0]").value("Conference was created"));

        mvc.perform(get("/getAllConference"))
                .andExpect(jsonPath("$.amount").value(5))
                .andExpect(jsonPath("$.arrayList[0].name").value("New & shiny"))
                .andExpect(jsonPath("$.arrayList[0].id_room").value(2))
                .andExpect(jsonPath("$.arrayList[0].name_room").value("Green mexican"))
                .andExpect(jsonPath("$.arrayList[0].capacity_room").value(68))
                .andExpect(jsonPath("$.arrayList[0].timee").value("09:30:00"));
    }

    @Test
    void createConferenceValidatesInput() throws Exception {
        String date = LocalDate.now().plusDays(3).toString();
        mvc.perform(post("/createconf")
                        .param("name", "X").param("id_room", "99").param("datee", date).param("timee", "09:30")
                        .param("admin_id", String.valueOf(ADMIN_ID)).param("admin_password", "admin"))
                .andExpect(jsonPath("$[0]").value("Incorrect room id"));
        mvc.perform(post("/createconf")
                        .param("name", "X").param("id_room", "1").param("datee", "2001-01-01").param("timee", "09:30")
                        .param("admin_id", String.valueOf(ADMIN_ID)).param("admin_password", "admin"))
                .andExpect(jsonPath("$[0]").value("The date must not be in the past"));
        mvc.perform(post("/createconf").param("name", "X"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0]").value("Please fill in all fields correctly"));
    }

    @Test
    void adminActionsRequireAdminPassword() throws Exception {
        String date = LocalDate.now().plusDays(3).toString();
        mvc.perform(post("/createconf")
                        .param("name", "X").param("id_room", "1").param("datee", date).param("timee", "09:30")
                        .param("admin_id", String.valueOf(ADMIN_ID)).param("admin_password", "wrong"))
                .andExpect(jsonPath("$[0]").value("Incorrect admin/manager password"));
        // a regular user is not an admin even with the right password
        mvc.perform(post("/cancelConf")
                        .param("conference_id", "1").param("admin_id", "2").param("admin_password", "demo"))
                .andExpect(jsonPath("$[0]").value("Incorrect admin/manager password"));
        mvc.perform(get("/getAllConference")).andExpect(jsonPath("$.amount").value(4));
    }

    @Test
    void removeParticipantFromConference() throws Exception {
        MockHttpSession demo = login("demo", "demo");
        mvc.perform(post("/joinConference").param("conference_id", "1").session(demo));

        mvc.perform(post("/removeParticipantFromConf")
                        .param("id_participant", "2").param("conference_id", "1")
                        .param("admin_id", String.valueOf(ADMIN_ID)).param("admin_password", "admin"))
                .andExpect(jsonPath("$[0]").value("Participant removed"));

        mvc.perform(get("/getAllConference"))
                .andExpect(jsonPath("$.arrayList[0].amount_participant").value(0))
                .andExpect(jsonPath("$.arrayList[0].id_participant").value(""));
        mvc.perform(get("/getAccount").session(demo))
                .andExpect(jsonPath("$.id_conference_participant").value(""));

        mvc.perform(post("/removeParticipantFromConf")
                        .param("id_participant", "2").param("conference_id", "1")
                        .param("admin_id", String.valueOf(ADMIN_ID)).param("admin_password", "admin"))
                .andExpect(jsonPath("$[0]").value("Participant is not registered at the conference"));
    }

    @Test
    void cancelConferenceAlsoUpdatesParticipants() throws Exception {
        MockHttpSession demo = login("demo", "demo");
        mvc.perform(post("/joinConference").param("conference_id", "1").session(demo));
        mvc.perform(post("/joinConference").param("conference_id", "2").session(demo));

        mvc.perform(post("/cancelConf")
                        .param("conference_id", "1")
                        .param("admin_id", String.valueOf(ADMIN_ID)).param("admin_password", "admin"))
                .andExpect(jsonPath("$[0]").value("Conference was cancelled"));

        mvc.perform(get("/getAllConference")).andExpect(jsonPath("$.amount").value(3));
        mvc.perform(get("/getAccount").session(demo))
                .andExpect(jsonPath("$.id_conference_participant").value("2,"));

        mvc.perform(post("/cancelConf")
                        .param("conference_id", "1")
                        .param("admin_id", String.valueOf(ADMIN_ID)).param("admin_password", "admin"))
                .andExpect(jsonPath("$[0]").value("Conference not found"));
    }

    @Test
    void changeConferenceTime() throws Exception {
        String date = LocalDate.now().plusDays(60).toString();
        mvc.perform(post("/changeConfTime")
                        .param("conference_id", "1").param("datee", date).param("timee", "18:15:00")
                        .param("admin_id", String.valueOf(ADMIN_ID)).param("admin_password", "admin"))
                .andExpect(jsonPath("$[0]").value("Conference time was changed"));

        mvc.perform(get("/getAllConference"))
                .andExpect(jsonPath("$.arrayList[3].id").value(1))
                .andExpect(jsonPath("$.arrayList[3].datee").value(date))
                .andExpect(jsonPath("$.arrayList[3].timee").value("18:15:00"));

        mvc.perform(post("/changeConfTime")
                        .param("conference_id", "999").param("datee", date).param("timee", "18:15")
                        .param("admin_id", String.valueOf(ADMIN_ID)).param("admin_password", "admin"))
                .andExpect(jsonPath("$[0]").value("Conference not found"));
    }

    // ---------- misc ----------

    @Test
    void stateChangingEndpointsRejectGet() throws Exception {
        mvc.perform(get("/joinConference").param("conference_id", "1"))
                .andExpect(status().isMethodNotAllowed());
        mvc.perform(get("/login").param("login", "admin").param("password", "admin"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void clientSideRoutesServeTheReactApp() throws Exception {
        for (String route : new String[]{"/conference", "/authorization", "/registration", "/creatConference", "/about"}) {
            mvc.perform(get(route))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/index.html"));
        }
    }

    @Test
    void debugEndpointsAreGone() throws Exception {
        mvc.perform(get("/checkCon")).andExpect(status().isNotFound());
    }

    // ---------- helpers ----------

    private MockHttpSession login(String login, String password) throws Exception {
        MvcResult result = mvc.perform(post("/login").param("login", login).param("password", password))
                .andExpect(jsonPath("$.login").value(login))
                .andReturn();
        return (MockHttpSession) result.getRequest().getSession(false);
    }

    private MockHttpSession register(String login, String password) throws Exception {
        MvcResult result = mvc.perform(post("/register")
                        .param("firstname", "First").param("lastname", "Last").param("birthday", "2000-01-31")
                        .param("login", login).param("password", password))
                .andExpect(jsonPath("$.login").value(login))
                .andReturn();
        return (MockHttpSession) result.getRequest().getSession(false);
    }
}
