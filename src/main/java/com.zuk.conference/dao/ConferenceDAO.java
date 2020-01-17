package com.zuk.conference.dao;

import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;

public interface ConferenceDAO {
    void insertConference();
    void deleteConferece();
    String getAllConference();
    String createConf(Participant participant,Conference conference);
    void updateConferance();

}
