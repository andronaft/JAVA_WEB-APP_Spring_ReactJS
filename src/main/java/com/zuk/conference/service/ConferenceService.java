package com.zuk.conference.service;

import com.zuk.conference.model.Conference;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Conference use cases. Methods that change data return a message for the user
 * and throw {@link ConferenceException} when the action is not allowed.
 */
public interface ConferenceService {

    List<Conference> getAll();

    String create(int adminId, String adminPassword, String name, int roomId, LocalDate datee, LocalTime timee);

    String cancel(int adminId, String adminPassword, int conferenceId);

    String addNewParticipant(int participantId, int conferenceId);

    String removeParticipant(int adminId, String adminPassword, int conferenceId, int participantId);

    String changeDateAndTime(int adminId, String adminPassword, int conferenceId, LocalDate datee, LocalTime timee);
}
