package com.zuk.conference.dao;

import com.zuk.conference.model.RoomJpaEntity;

import java.util.ArrayList;

public interface IRoom {
    RoomJpaEntity findById(int id);
    ArrayList<RoomJpaEntity> findAll();
    RoomJpaEntity save(RoomJpaEntity roomJpaEntity);
}
