package com.zuk.conference.dao;

import com.zuk.conference.model.ParticipantJpaEntity;

import java.util.ArrayList;

public interface IParticipant {
    ParticipantJpaEntity findById(int id);
    ParticipantJpaEntity findByLogin(String login);
    ArrayList<ParticipantJpaEntity> findAll();
    ParticipantJpaEntity save(ParticipantJpaEntity participantJpaEntity);
}
