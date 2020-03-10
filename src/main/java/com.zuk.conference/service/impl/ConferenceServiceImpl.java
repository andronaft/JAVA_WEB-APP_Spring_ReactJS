package com.zuk.conference.service.impl;

import com.zuk.conference.auxiliary.JsonStringMaker;
import com.zuk.conference.dao.daoimpl.ConferenceDAOImpl;
import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;
import com.zuk.conference.service.ConferenceService;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConferenceServiceImpl  implements ConferenceService {

    @Autowired
    JsonStringMaker jsonStringMaker;

    ConferenceDAOImpl conferenceDAO = new ConferenceDAOImpl();

    @Override
    public String joinNewParticipant(Participant participant, int conferenceId) {//TODO Do somthing
        Conference conferencefind = conferenceDAO.findById(conferenceId);
        System.out.println(conferencefind.toString());
        System.out.println(conferencefind.getName());
        ArrayList arrayList = new ArrayList();

        arrayList.add(conferencefind);
        arrayList.add(conferencefind);
        jsonStringMaker.listToJson(arrayList);
        return null;
    }
}
