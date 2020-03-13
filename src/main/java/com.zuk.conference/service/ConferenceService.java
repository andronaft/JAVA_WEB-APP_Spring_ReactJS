package com.zuk.conference.service;

import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;

public interface ConferenceService {
    String joinNewParticipant(int participantId, int conferenceId);
}
