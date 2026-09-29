package com.zuk.conference.service.impl;

import com.zuk.conference.auxiliary.IdList;
import com.zuk.conference.dao.ConferenceDAO;
import com.zuk.conference.dao.ParticipantDAO;
import com.zuk.conference.dao.RoomDAO;
import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;
import com.zuk.conference.model.Room;
import com.zuk.conference.service.ConferenceException;
import com.zuk.conference.service.ConferenceService;
import com.zuk.conference.service.ParticipantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Every write locks the conference row first (and then the participant rows),
 * so concurrent joins can't overbook a room or lose each other's updates.
 */
@Service
@Transactional
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceDAO conferenceDAO;
    private final ParticipantDAO participantDAO;
    private final RoomDAO roomDAO;
    private final ParticipantService participantService;

    public ConferenceServiceImpl(ConferenceDAO conferenceDAO, ParticipantDAO participantDAO,
                                 RoomDAO roomDAO, ParticipantService participantService) {
        this.conferenceDAO = conferenceDAO;
        this.participantDAO = participantDAO;
        this.roomDAO = roomDAO;
        this.participantService = participantService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Conference> getAll() {
        return conferenceDAO.findUpcoming();
    }

    @Override
    public String addNewParticipant(int participantId, int conferenceId) {
        Conference conference = lockConference(conferenceId);
        Participant participant = participantDAO.findByIdForUpdate(participantId);
        if (participant == null) {
            throw new ConferenceException("Participant not found");
        }
        if (IdList.contains(conference.getId_participant(), participantId)) {
            throw new ConferenceException("You have already joined this conference");
        }
        if (conference.getAmount_participant() >= conference.getCapacity_room()) {
            throw new ConferenceException("Sorry, the conference is full");
        }
        conferenceDAO.updateIdParticipant(
                IdList.add(conference.getId_participant(), participantId),
                conference.getAmount_participant() + 1,
                conferenceId);
        participantDAO.updateIdConference(
                IdList.add(participant.getId_conference_participant(), conferenceId),
                participantId);
        return "You joined the conference \"" + conference.getName() + "\"";
    }

    @Override
    public String removeParticipant(int adminId, String adminPassword, int conferenceId, int participantId) {
        requireAdmin(adminId, adminPassword);
        Conference conference = lockConference(conferenceId);
        if (!IdList.contains(conference.getId_participant(), participantId)) {
            throw new ConferenceException("Participant is not registered at the conference");
        }
        conferenceDAO.updateIdParticipant(
                IdList.remove(conference.getId_participant(), participantId),
                Math.max(0, conference.getAmount_participant() - 1),
                conferenceId);
        removeConferenceFromParticipant(participantId, conferenceId);
        return "Participant removed";
    }

    @Override
    public String changeDateAndTime(int adminId, String adminPassword, int conferenceId,
                                    LocalDate datee, LocalTime timee) {
        requireAdmin(adminId, adminPassword);
        requireFutureDate(datee);
        if (!conferenceDAO.updateDateAndTime(conferenceId, datee, timee)) {
            throw new ConferenceException("Conference not found");
        }
        return "Conference time was changed";
    }

    @Override
    public String cancel(int adminId, String adminPassword, int conferenceId) {
        requireAdmin(adminId, adminPassword);
        Conference conference = lockConference(conferenceId);
        for (int participantId : IdList.parse(conference.getId_participant())) {
            removeConferenceFromParticipant(participantId, conferenceId);
        }
        conferenceDAO.delete(conferenceId);
        return "Conference was cancelled";
    }

    @Override
    public String create(int adminId, String adminPassword, String name, int roomId,
                         LocalDate datee, LocalTime timee) {
        requireAdmin(adminId, adminPassword);
        if (name == null || name.trim().isEmpty()) {
            throw new ConferenceException("Conference name is required");
        }
        requireFutureDate(datee);
        Room room = roomDAO.findById(roomId);
        if (room == null) {
            throw new ConferenceException("Incorrect room id");
        }
        Conference conference = Conference.newBuilder()
                .setName(name.trim())
                .setId_room(room.getId())
                .setName_room(room.getName())
                .setCapacity_room(room.getCapacity())
                .setAmount_participant(0)
                .setDatee(datee)
                .setTimee(timee)
                .build();
        conferenceDAO.save(conference);
        return "Conference was created";
    }

    private Conference lockConference(int conferenceId) {
        Conference conference = conferenceDAO.findByIdForUpdate(conferenceId);
        if (conference == null) {
            throw new ConferenceException("Conference not found");
        }
        return conference;
    }

    private void removeConferenceFromParticipant(int participantId, int conferenceId) {
        Participant participant = participantDAO.findByIdForUpdate(participantId);
        if (participant != null) {
            participantDAO.updateIdConference(
                    IdList.remove(participant.getId_conference_participant(), conferenceId),
                    participantId);
        }
    }

    private void requireAdmin(int adminId, String adminPassword) {
        if (!participantService.isAdmin(adminId, adminPassword)) {
            throw new ConferenceException("Incorrect admin/manager password");
        }
    }

    private static void requireFutureDate(LocalDate datee) {
        if (datee.isBefore(LocalDate.now())) {
            throw new ConferenceException("The date must not be in the past");
        }
    }
}
