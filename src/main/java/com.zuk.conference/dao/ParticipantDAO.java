package com.zuk.conference.dao;

import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;
import java.io.FileNotFoundException;

public interface ParticipantDAO {//TODO make abstract class
    String insertParticipant(Participant participant) throws FileNotFoundException;
    String getParticipant(Participant participant);
    String login(Participant participant);
    String joinConferrence(Participant participant, Conference conference);
    Boolean isadmin(Participant admin);
    Boolean removeFromConference(Participant participant,int idConference);
}
