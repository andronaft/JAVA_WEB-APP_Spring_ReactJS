package com.zuk.conference.service;

import com.zuk.conference.model.Participant;

/** Participant use cases. Errors are reported with {@link ConferenceException}. */
public interface ParticipantService {

    Participant login(String login, String password);

    Participant register(Participant participant);

    Participant getParticipant(int id);

    boolean isAdmin(int id, String password);
}
