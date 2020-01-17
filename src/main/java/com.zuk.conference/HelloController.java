package com.zuk.conference;

        import com.fasterxml.jackson.core.JsonProcessingException;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.fasterxml.jackson.databind.util.JSONPObject;
        import com.zuk.conference.conection.ConnectionManager;
        import com.zuk.conference.dao.daoimpl.ConferenceDAOImpl;
        import com.zuk.conference.dao.daoimpl.ParticipantDAOImpl;
        import com.zuk.conference.model.Conference;
        import com.zuk.conference.model.Participant;
        import com.zuk.conference.model.Room;

        import java.sql.*;

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

    @RequestMapping("/getAllConference")
    String getAllConference() {
        ConferenceDAOImpl conferenceDAO = new ConferenceDAOImpl();
        return (conferenceDAO.getAllConference());
    }


    @RequestMapping("/register")
    String calc(@RequestParam String firstname,@RequestParam String lastname,@RequestParam Date birthday,@RequestParam String login,@RequestParam String password) throws JsonProcessingException {
        ObjectMapper objectMapper= new ObjectMapper();

        System.out.println(firstname+ lastname+birthday+login+password);

        Participant participant = new Participant(firstname,lastname,birthday,login,password);

        ParticipantDAOImpl participantDAO= new ParticipantDAOImpl();

        return (participantDAO.insertParticipant(participant));

    }
    @RequestMapping("/createconf")
    String createConf(@RequestParam String name ,@RequestParam int id_room ,@RequestParam Date datee ,@RequestParam Time timee,@RequestParam int admin_id, @RequestParam String admin_password){
        Participant participant = new Participant();
        participant.setId(admin_id);participant.setPassword(admin_password);
        Conference conference = new Conference();
        conference.setName(name);conference.setAmount_participant(0);conference.setId_room(id_room);conference.setDatee(datee);conference.setTimee(timee);

        ConferenceDAOImpl conferenceDAO = new ConferenceDAOImpl();


        return (conferenceDAO.createConf(participant,conference));
    }
    @RequestMapping("/login")
    String login(@RequestParam String login ,@RequestParam String password ){
        Participant participant = new Participant();
        participant.setLogin(login);participant.setPassword(password);

        ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();

    return (participantDAO.login(participant));
    }
}
