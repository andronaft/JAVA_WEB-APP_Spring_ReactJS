package com.zuk.conference.service;

import com.zuk.conference.auxiliary.JsonStringMaker;
import com.zuk.conference.dao.impl.ConferenceDAOImpl;
import com.zuk.conference.dao.impl.ParticipantDAOImpl;
import com.zuk.conference.dao.impl.RoomDAOImpl;
import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;
import com.zuk.conference.service.impl.ParticipantServiceImpl;

public abstract class ConferenceService {

    protected JsonStringMaker jsonStringMaker;

    protected ParticipantServiceImpl participantService;

    protected ConferenceDAOImpl conferenceDAO;
    protected ParticipantDAOImpl participantDAO;
    protected RoomDAOImpl roomDAO;
    protected String message;


    public ConferenceService() {
        participantDAO = new ParticipantDAOImpl();
        conferenceDAO = new ConferenceDAOImpl();
        roomDAO = new RoomDAOImpl();
        participantService = new ParticipantServiceImpl();
        jsonStringMaker = new JsonStringMaker();
    }

    protected abstract String getAll();
    protected abstract String create(Participant admin, Conference conference);
    protected abstract String cancel(Participant admin,int conferenceId);
    protected abstract String addNewParticipant(int participantId, int conferenceId);
    protected abstract String removeParticipant(Participant admin,int conferenceId,int participantId);
    protected abstract String changeDateAndTime(Participant admin,Conference conference);
}
