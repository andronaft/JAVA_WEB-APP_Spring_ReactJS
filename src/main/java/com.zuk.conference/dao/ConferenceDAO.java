package com.zuk.conference.dao;

import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;

public interface ConferenceDAO {

    String joinNewParticipant(Participant participant,Conference conference);
    String cancelConferece(Participant admin,Conference conference);
    String getAllConference();
    String createConf(Participant participant,Conference conference);
    String removeParticipant(Participant admin,Participant participant,Conference conference);
    String changeTime(Participant participant, Conference conference);

}
