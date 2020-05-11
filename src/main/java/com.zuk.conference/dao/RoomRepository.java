package com.zuk.conference.dao;

import com.zuk.conference.model.RoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<RoomJpaEntity, Integer> {
}
