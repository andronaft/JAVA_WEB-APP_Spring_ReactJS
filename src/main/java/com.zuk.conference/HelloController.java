package com.zuk.conference;

        import com.fasterxml.jackson.core.JsonProcessingException;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.fasterxml.jackson.databind.util.JSONPObject;
        import com.zuk.conference.conection.ConnectionManager;
        import com.zuk.conference.dao.daoimpl.ParticipantDAOImpl;
        import com.zuk.conference.model.Participant;
        import com.zuk.conference.model.Room;
        import java.sql.Date;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
        import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
        import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.RestController;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;

@RestController
public class HelloController {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    ConnectionManager cm = new ConnectionManager();
    Connection con = cm.getConnection();

    @RequestMapping("/hello")
    String hello() {
        return "Hello World!";
    }


    @RequestMapping("/register")
    String calc(@RequestParam String firstname,@RequestParam String lastname,@RequestParam Date birthday,@RequestParam String login,@RequestParam String password) throws JsonProcessingException {
        ObjectMapper objectMapper= new ObjectMapper();

        System.out.println(firstname+ lastname+birthday+login+password);

        Participant participant = new Participant(firstname,lastname,birthday,login,password);

        ParticipantDAOImpl participantDAO= new ParticipantDAOImpl();

        return (participantDAO.insertParticipant(participant));

    }
    @RequestMapping("/login")
    String login(@RequestParam String login ,@RequestParam String password ){
        Participant participant = new Participant();
        participant.setLogin(login);participant.setPassword(password);

        ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
        participantDAO.login(participant);
    return (" ");
    }
}
