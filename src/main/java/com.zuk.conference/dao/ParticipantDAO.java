package com.zuk.conference.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuk.conference.model.Participant;

import java.io.FileNotFoundException;
import java.sql.Connection;

public interface ParticipantDAO {
    String insertParticipant(Participant participant) throws FileNotFoundException;
    ParticipantDAO getParticipant();
    String login(Participant participant);
    Boolean isadmin(Participant participant);
}
