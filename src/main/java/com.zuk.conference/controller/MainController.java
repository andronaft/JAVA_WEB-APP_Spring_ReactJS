package com.zuk.conference.controller;

        import com.zuk.conference.conection.ConnectionManager;
        import com.zuk.conference.model.Conference;
        import com.zuk.conference.model.Participant;
        import java.sql.*;

        import com.zuk.conference.service.impl.ConferenceServiceImpl;
        import com.zuk.conference.service.impl.ParticipantServiceImpl;
        import org.apache.log4j.Logger;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.ResponseBody;
        import org.springframework.web.bind.annotation.RestController;
        import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private static final Logger logger = Logger.getLogger(MainController.class);

    ConferenceServiceImpl conferenceService = new ConferenceServiceImpl();
    ParticipantServiceImpl participantService = new ParticipantServiceImpl();

    @RequestMapping("/hello")
    @ResponseBody
    ModelAndView hello() {
        if(logger.isDebugEnabled()){
            logger.debug("getWelcome is executed!");
        }

        //logs exception
        //logger.error("This is Error message", new Exception("Testing"));
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
        return conferenceService.getAll();
    }

    @RequestMapping("/getAccount")
    String getAccont(@RequestParam int id) {
        return participantService.getParticipant(id);
    }


    @RequestMapping("/register")
    String calc(@RequestParam String firstname,@RequestParam String lastname,@RequestParam Date birthday,@RequestParam String login,@RequestParam String password) {
        Participant participant = Participant.newBuilder()
                .setFirstName(firstname)
                .setLastName(lastname)
                .setBirthDay(birthday)
                .setLogin(login)
                .setPassword(password)
                .build();
        return participantService.register(participant);
    }

    @RequestMapping("/removeParticipantFromConf")
    String createConf(@RequestParam int id_participant , @RequestParam int conference_id , @RequestParam int admin_id , @RequestParam String admin_password){
        Participant admin = Participant.newBuilder()
                .setId(admin_id)
                .setPassword(admin_password)
                .build();
        return conferenceService.removeParticipant(admin,conference_id,id_participant);
    }

    @RequestMapping("/createconf")
    String createConference(@RequestParam String name ,@RequestParam int id_room ,@RequestParam Date datee ,@RequestParam Time timee,@RequestParam int admin_id, @RequestParam String admin_password){
        Participant admin = Participant.newBuilder()
                .setId(admin_id)
                .setPassword(admin_password)
                .build();
        Conference conference = Conference.newBuilder()
                .setName(name)
                .setAmount_participant(0)
                .setId_room(id_room)
                .setDatee(datee)
                .setTimee(timee).build();

        return conferenceService.create(admin,conference);
    }

    @RequestMapping("/cancelConf")
    String cancelConference(@RequestParam int conference_id , @RequestParam int admin_id , @RequestParam String admin_password){
        Participant admin = Participant.newBuilder()
                .setId(admin_id)
                .setPassword(admin_password)
                .build();

        return conferenceService.cancel(admin,conference_id);
    }

    @RequestMapping("/changeConfTime")
    String cancelConf(@RequestParam int conference_id , @RequestParam int admin_id , @RequestParam String admin_password, @RequestParam Date datee, @RequestParam Time timee){
        Participant admin = Participant.newBuilder()
                .setId(admin_id)
                .setPassword(admin_password)
                .build();
        Conference conference = Conference.newBuilder()
                .setId(conference_id)
                .setDatee(datee)
                .setTimee(timee)
                .build();

        return conferenceService.changeDateAndTime(admin,conference);
    }

    @RequestMapping("/login")
    String login(@RequestParam String login ,@RequestParam String password ){
        Participant participant = Participant.newBuilder()
                .setLogin(login)
                .setPassword(password)
                .build();

        return participantService.login(participant);
    }

    @RequestMapping("/joinConference")
    String joinConference(@RequestParam int conference_id , @RequestParam int user_id ){
        return  conferenceService.addNewParticipant(user_id,conference_id);
    }

    @RequestMapping("/checkCon")
    String checkCon(){
        ConnectionManager connectionManager = new ConnectionManager();
        return String.valueOf(connectionManager.getConnection());
    }

    @RequestMapping("/findConferenceById")
    String findConferenceById(@RequestParam int conferenceId,@RequestParam int participantId){
    return "----";
    }
}
