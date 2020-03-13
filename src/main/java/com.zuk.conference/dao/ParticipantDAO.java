package com.zuk.conference.dao;

import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;
import java.io.FileNotFoundException;

public interface ParticipantDAO {//TODO make abstract class

    Participant findById(int id);
    void updateIdConference(String idConference, int id);

    String insertParticipant(Participant participant) throws FileNotFoundException;
    String getParticipant(Participant participant);
    String login(Participant participant);
    Boolean isadmin(Participant admin);
    Boolean removeFromConference(Participant participant,int idConference);
}
