package com.zuk.conference.dao;

import com.zuk.conference.model.ConferenceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRepository extends JpaRepository<ConferenceJpaEntity, Integer> {
}
