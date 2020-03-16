package com.zuk.conference.service;

import com.zuk.conference.dao.impl.ParticipantDAOImpl;

public abstract class ParticipantService {

    protected ParticipantDAOImpl participantDAO;

    public ParticipantService() {
        this.participantDAO = new ParticipantDAOImpl();
    }

    protected abstract boolean removeFromConference(int id, int conferenceId);
}
