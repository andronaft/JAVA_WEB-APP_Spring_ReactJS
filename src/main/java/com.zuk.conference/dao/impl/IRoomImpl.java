package com.zuk.conference.dao.impl;

import com.zuk.conference.dao.IRoom;
import com.zuk.conference.dao.RoomRepository;
import com.zuk.conference.model.RoomJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
@Transactional
@AllArgsConstructor
public class IRoomImpl implements IRoom {
    private final RoomRepository roomRepository;
    @Override
    public RoomJpaEntity findById(int id) {
        return null;
    }

    @Override
    public ArrayList<RoomJpaEntity> findAll() {
        return null;
    }

    @Override
    public RoomJpaEntity save(RoomJpaEntity roomJpaEntity) {
        return null;
    }
}
