package com.zuk.conference.service;

import com.zuk.conference.auxiliary.JsonStringMaker;
import com.zuk.conference.dao.impl.RoomDAOImpl;

public abstract class RoomService {

    protected JsonStringMaker jsonStringMaker;

    protected RoomDAOImpl roomDAO;

    public RoomService(){
        roomDAO = new RoomDAOImpl();
        jsonStringMaker = new JsonStringMaker();
    }

    protected abstract String getById(int id);
}
