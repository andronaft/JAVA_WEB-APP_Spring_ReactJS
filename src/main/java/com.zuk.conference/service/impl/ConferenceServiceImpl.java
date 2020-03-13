package com.zuk.conference.service.impl;

import com.zuk.conference.auxiliary.JsonStringMaker;
import com.zuk.conference.dao.daoimpl.ConferenceDAOImpl;
import com.zuk.conference.dao.daoimpl.ParticipantDAOImpl;
import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;
import com.zuk.conference.service.ConferenceService;


import java.util.ArrayList;


public class ConferenceServiceImpl  implements ConferenceService {


    JsonStringMaker jsonStringMaker = new JsonStringMaker();

    ConferenceDAOImpl conferenceDAO = new ConferenceDAOImpl();
    ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();

    @Override
    public String joinNewParticipant(int participantId, int conferenceId) {

        Conference conference = conferenceDAO.findById(conferenceId);

        if(conference.getAmount_participant()<conference.getCapacity_room()){
            String participantIds = conference.getId_participant();
            if (participantIds == null) {
                participantIds = "";
            }

            String[] strArray = participantIds.split(",");
            int[] intArray = new int[strArray.length];

            boolean containParticipant = false;
            if(participantIds!="") {
                for (int i = 0; i < strArray.length; i++) {
                    try {
                        intArray[i] = Integer.parseInt(strArray[i]);
                        if (intArray[i] == participantId) {
                            containParticipant = true;
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            if(!containParticipant){
                participantIds += (participantIds + ",");
                conferenceDAO.updateIdParticipant(participantIds,(conference.getAmount_participant()+1),conferenceId);

            }
        }


        return jsonStringMaker.objectToJson(conference);
        //return conferenceDAO.isFilled(conferenceId).toString();
    }
}
