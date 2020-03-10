package com.zuk.conference.service;

import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;

public interface ConferenceService {
    String joinNewParticipant(Participant participant, int conferenceId);
}
