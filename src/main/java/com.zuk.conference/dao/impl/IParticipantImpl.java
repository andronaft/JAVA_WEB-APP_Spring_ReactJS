package com.zuk.conference.dao.impl;

import com.zuk.conference.dao.IParticipant;
import com.zuk.conference.dao.ParticipantRepository;
import com.zuk.conference.model.Participant;
import com.zuk.conference.model.ParticipantJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
@Transactional
@AllArgsConstructor
public class IParticipantImpl implements IParticipant {
    private final ParticipantRepository participantRepository;
    @Override
    public ParticipantJpaEntity findById(int id) {
        return participantRepository.getOne(id);
    }

    @Override
    public ParticipantJpaEntity findByLogin(String login) {
        return participantRepository.findByLogin(login);
    }

    @Override
    public ArrayList<ParticipantJpaEntity> findAll() {
        return (ArrayList<ParticipantJpaEntity>) participantRepository.findAll();
    }

    @Override
    public ParticipantJpaEntity save(ParticipantJpaEntity participantJpaEntity) {
        return participantRepository.save(participantJpaEntity);
    }
}
