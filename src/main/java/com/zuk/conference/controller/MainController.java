package com.zuk.conference.controller;

import com.zuk.conference.auxiliary.ArrayWithAmount;
import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;
import com.zuk.conference.service.ConferenceException;
import com.zuk.conference.service.ConferenceService;
import com.zuk.conference.service.ParticipantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.TypeMismatchException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * JSON API used by the React client.
 * <p>
 * Successful reads return the object itself, actions return a one element
 * array with a message for the user: {@code ["Conference was created"]}.
 * The logged in user is kept in the HTTP session, so a client can't act on
 * behalf of another user by changing an id in the request.
 */
@RestController
public class MainController {

    static final String SESSION_USER_ID = "userId";

    private final ConferenceService conferenceService;
    private final ParticipantService participantService;

    public MainController(ConferenceService conferenceService, ParticipantService participantService) {
        this.conferenceService = conferenceService;
        this.participantService = participantService;
    }

    @GetMapping("/getAllConference")
    ArrayWithAmount<Conference> getAllConference() {
        return new ArrayWithAmount<>(conferenceService.getAll());
    }

    @GetMapping("/getAccount")
    Participant getAccount(HttpSession session) {
        return participantService.getParticipant(currentUserId(session));
    }

    @PostMapping("/register")
    Participant register(@RequestParam("firstname") String firstName,
                         @RequestParam("lastname") String lastName,
                         @RequestParam("birthday") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthday,
                         @RequestParam("login") String login,
                         @RequestParam("password") String password,
                         HttpServletRequest request) {
        Participant participant = participantService.register(Participant.newBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBirthDay(birthday)
                .setLogin(login)
                .setPassword(password)
                .build());
        startSession(request, participant);
        return participant;
    }

    @PostMapping("/login")
    Participant login(@RequestParam("login") String login,
                      @RequestParam("password") String password,
                      HttpServletRequest request) {
        Participant participant = participantService.login(login, password);
        startSession(request, participant);
        return participant;
    }

    @PostMapping("/logout")
    List<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return message("You have logged out");
    }

    @PostMapping("/joinConference")
    List<String> joinConference(@RequestParam("conference_id") int conferenceId, HttpSession session) {
        return message(conferenceService.addNewParticipant(currentUserId(session), conferenceId));
    }

    @PostMapping("/removeParticipantFromConf")
    List<String> removeParticipant(@RequestParam("id_participant") int participantId,
                                   @RequestParam("conference_id") int conferenceId,
                                   @RequestParam("admin_id") int adminId,
                                   @RequestParam("admin_password") String adminPassword) {
        return message(conferenceService.removeParticipant(adminId, adminPassword, conferenceId, participantId));
    }

    @PostMapping("/createconf")
    List<String> createConference(@RequestParam("name") String name,
                                  @RequestParam("id_room") int roomId,
                                  @RequestParam("datee") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datee,
                                  @RequestParam("timee") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime timee,
                                  @RequestParam("admin_id") int adminId,
                                  @RequestParam("admin_password") String adminPassword) {
        return message(conferenceService.create(adminId, adminPassword, name, roomId, datee, timee));
    }

    @PostMapping("/cancelConf")
    List<String> cancelConference(@RequestParam("conference_id") int conferenceId,
                                  @RequestParam("admin_id") int adminId,
                                  @RequestParam("admin_password") String adminPassword) {
        return message(conferenceService.cancel(adminId, adminPassword, conferenceId));
    }

    @PostMapping("/changeConfTime")
    List<String> changeConferenceTime(@RequestParam("conference_id") int conferenceId,
                                      @RequestParam("admin_id") int adminId,
                                      @RequestParam("admin_password") String adminPassword,
                                      @RequestParam("datee") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datee,
                                      @RequestParam("timee") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime timee) {
        return message(conferenceService.changeDateAndTime(adminId, adminPassword, conferenceId, datee, timee));
    }

    @ExceptionHandler(ConferenceException.class)
    List<String> handleConferenceException(ConferenceException e) {
        return message(e.getMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, TypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    List<String> handleBadRequest() {
        return message("Please fill in all fields correctly");
    }

    private static int currentUserId(HttpSession session) {
        Object userId = session.getAttribute(SESSION_USER_ID);
        if (!(userId instanceof Integer)) {
            throw new ConferenceException("You must log in first");
        }
        return (Integer) userId;
    }

    private static void startSession(HttpServletRequest request, Participant participant) {
        // new session id on every login (prevents session fixation)
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        request.getSession(true).setAttribute(SESSION_USER_ID, participant.getId());
    }

    private static List<String> message(String text) {
        return List.of(text);
    }
}
