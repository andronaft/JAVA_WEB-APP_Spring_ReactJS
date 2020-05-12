package com.zuk.conference.dao.impl;

import com.zuk.conference.dao.ConferenceRepository;
import com.zuk.conference.dao.IConference;
import com.zuk.conference.model.ConferenceJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
@Transactional
@AllArgsConstructor
public class IConferenceImpl implements IConference {

    private final ConferenceRepository conferenceRepository;



    @Override
    public ConferenceJpaEntity findById(int id) {
        return conferenceRepository.getOne(id);
    }

    @Override
    public ArrayList<ConferenceJpaEntity> findAll() {
        return (ArrayList<ConferenceJpaEntity>) conferenceRepository.findAll();
    }

    @Override
    public ConferenceJpaEntity save(ConferenceJpaEntity conferenceJpaEntity) {
        return conferenceRepository.save(conferenceJpaEntity);
    }

    @Override
    public void delete(ConferenceJpaEntity conferenceJpaEntity) {
        conferenceRepository.delete(conferenceJpaEntity);
    }
}
