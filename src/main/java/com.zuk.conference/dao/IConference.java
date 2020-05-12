package com.zuk.conference.dao;

import com.zuk.conference.model.Conference;
import com.zuk.conference.model.ConferenceJpaEntity;

import java.util.ArrayList;

public interface IConference {
    ConferenceJpaEntity findById(int id);
    ArrayList<ConferenceJpaEntity> findAll();
    ConferenceJpaEntity save(ConferenceJpaEntity conferenceJpaEntity);
    void delete(ConferenceJpaEntity conferenceJpaEntity);
}
