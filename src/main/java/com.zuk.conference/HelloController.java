package com.zuk.conference;
        import com.fasterxml.jackson.core.JsonProcessingException;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.zuk.conference.conection.ConnectionManager;
        import com.zuk.conference.dao.daoimpl.ConferenceDAOImpl;
        import com.zuk.conference.dao.daoimpl.ParticipantDAOImpl;
        import com.zuk.conference.model.Conference;
        import com.zuk.conference.model.Participant;
        import java.sql.*;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.ResponseBody;
        import org.springframework.web.bind.annotation.RestController;
        import org.springframework.web.servlet.ModelAndView;

@RestController
public class HelloController {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    ConnectionManager cm = new ConnectionManager();
    Connection con = cm.getConnection();

    @RequestMapping("/hello")
    @ResponseBody
    ModelAndView hello() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

    @RequestMapping("/about")
    @ResponseBody
    ModelAndView about() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

    @RequestMapping("/conference")
    @ResponseBody
    ModelAndView conference() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

    @RequestMapping("/authorization")
    @ResponseBody
    ModelAndView authorization() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

    @RequestMapping("/getAllConference")
    String getAllConference() {
        ConferenceDAOImpl conferenceDAO = new ConferenceDAOImpl();
        return (conferenceDAO.getAllConference());
    }

    @RequestMapping("/getAccount")
    String getAccont(@RequestParam int id) {
        Participant participant = new Participant();
        participant.setId(id);
        ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();

        return participantDAO.getParticipant(participant);
    }


    @RequestMapping("/register")
    String calc(@RequestParam String firstname,@RequestParam String lastname,@RequestParam Date birthday,@RequestParam String login,@RequestParam String password) throws JsonProcessingException {
        ObjectMapper objectMapper= new ObjectMapper();

        System.out.println(firstname+ lastname+birthday+login+password);

        Participant participant = new Participant(firstname,lastname,birthday,login,password);

        ParticipantDAOImpl participantDAO= new ParticipantDAOImpl();

        return (participantDAO.insertParticipant(participant));

    }

    @RequestMapping("/removeParticipantFromConf")
    String createConf(@RequestParam int id_participant , @RequestParam int conference_id , @RequestParam int admin_id , @RequestParam String admin_password){
        Participant admin = new Participant();
        admin.setId(admin_id);admin.setPassword(admin_password);
        Participant participant = new Participant();
        participant.setId(id_participant);
        Conference conference = new Conference();
        conference.setId(conference_id);
        System.out.println(id_participant +"id_pars"+ conference_id +"id_conf"+ admin_id +"id_admin"+ admin_password +"pass");

        ConferenceDAOImpl conferenceDAO = new ConferenceDAOImpl();

        return (conferenceDAO.removeParticipant(admin,participant,conference));
    }

    @RequestMapping("/createconf")
    String createConf(@RequestParam String name ,@RequestParam int id_room ,@RequestParam Date datee ,@RequestParam Time timee,@RequestParam int admin_id, @RequestParam String admin_password){
        Participant admin = new Participant();
        admin.setId(admin_id);admin.setPassword(admin_password);
        Conference conference = new Conference();
        conference.setName(name);conference.setAmount_participant(0);conference.setId_room(id_room);conference.setDatee(datee);conference.setTimee(timee);

        ConferenceDAOImpl conferenceDAO = new ConferenceDAOImpl();

        return (conferenceDAO.createConf(admin,conference));
    }

    @RequestMapping("/cancelConf")
    String cancelConf(@RequestParam int conference_id , @RequestParam int admin_id , @RequestParam String admin_password){
        Participant admin = new Participant();
        admin.setId(admin_id);admin.setPassword(admin_password);
        Conference conference = new Conference();
        conference.setId(conference_id);

        ConferenceDAOImpl conferenceDAO = new ConferenceDAOImpl();

        return (conferenceDAO.cancelConferece(admin,conference));
    }

    @RequestMapping("/changeConfTime")
    String cancelConf(@RequestParam int conference_id , @RequestParam int admin_id , @RequestParam String admin_password, @RequestParam Date datee, @RequestParam Time timee){
        Participant admin = new Participant();
        admin.setId(admin_id);admin.setPassword(admin_password);
        Conference conference = new Conference();
        conference.setId(conference_id);conference.setDatee(datee);conference.setTimee(timee);

        ConferenceDAOImpl conferenceDAO = new ConferenceDAOImpl();

        return (conferenceDAO.changeTime(admin,conference));
    }

    @RequestMapping("/login")
    String login(@RequestParam String login ,@RequestParam String password ){
        Participant participant = new Participant();
        participant.setLogin(login);participant.setPassword(password);

        ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();

        return (participantDAO.login(participant));
    }

    @RequestMapping("/joinConference")
    String joinConference(@RequestParam int conference_id , @RequestParam int user_id ){
        Participant participant = new Participant();
        participant.setId(user_id);
        Conference conference = new Conference();
        conference.setId(conference_id);

        boolean ad = (0 == (7 * (4 ^ (2))%(3)));
        ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();

        return (participantDAO.joinConferrence(participant,conference));
    }

}
