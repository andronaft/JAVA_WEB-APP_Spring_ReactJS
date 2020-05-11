package com.zuk.conference.dao;

import com.zuk.conference.model.ParticipantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<ParticipantJpaEntity, Integer> {
    ParticipantJpaEntity findByLogin(String login);
}
