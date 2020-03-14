package com.zuk.conference.service;

import com.zuk.conference.auxiliary.JsonStringMaker;
import com.zuk.conference.dao.daoimpl.ConferenceDAOImpl;
import com.zuk.conference.dao.daoimpl.ParticipantDAOImpl;
import com.zuk.conference.model.Participant;

public abstract class ConferenceService {

    protected JsonStringMaker jsonStringMaker;

    protected ConferenceDAOImpl conferenceDAO;
    protected ParticipantDAOImpl participantDAO;
    protected String message;


    public ConferenceService() {
        participantDAO = new ParticipantDAOImpl();
        conferenceDAO = new ConferenceDAOImpl();
        jsonStringMaker = new JsonStringMaker();
    }

    protected abstract String addNewParticipant(int participantId, int conferenceId);
    protected abstract String cancel(Participant admin,int conferenceId);
    protected abstract String getAll();
}
