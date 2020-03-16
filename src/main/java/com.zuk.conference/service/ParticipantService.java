package com.zuk.conference.service;

import com.zuk.conference.auxiliary.JsonStringMaker;
import com.zuk.conference.dao.impl.ParticipantDAOImpl;
import com.zuk.conference.model.Participant;

public abstract class ParticipantService {

    protected JsonStringMaker jsonStringMaker;

    protected ParticipantDAOImpl participantDAO;

    public ParticipantService() {
        this.participantDAO = new ParticipantDAOImpl();
        this.jsonStringMaker = new JsonStringMaker();
    }

    protected abstract boolean removeFromConference(int id, int conferenceId);
    protected abstract String login(Participant participant);
    protected abstract String register(Participant participant);
    protected abstract String getParticipant(int id);

}
