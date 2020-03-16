package com.zuk.conference.service.impl;

import com.zuk.conference.service.RoomService;

public class RoomServiceImpl extends RoomService {

    @Override
    public String getById(int id) {
        return jsonStringMaker.objectToJson(roomDAO.findById(id));
    }
}
