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
    String error;

    @Override
    public String addNewParticipant(int participantId, int conferenceId) {
        error = "Message:";
        Conference conference = conferenceDAO.findById(conferenceId);

        if(conference.getAmount_participant()<conference.getCapacity_room()){
            String participantIds = conference.getId_participant();

            if (participantIds == null) {
                participantIds = "";
            }

            String[] strArray = participantIds.split(",");
            int[] intArray = new int[strArray.length];
            System.out.println("size arr" + intArray.length);
            boolean containParticipant = false;
            System.out.println(strArray.length);
            System.out.println(strArray[0]);
            if(!strArray[0].isEmpty()) {
                for (int i = 0; i < strArray.length; i++) {
                    try {
                        intArray[i] = Integer.parseInt(strArray[i]);
                        if (intArray[i] == participantId) {
                            containParticipant = true;
                            error+="your are already join.";
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            if(!containParticipant){
                participantIds += (participantId + ",");
                conferenceDAO.updateIdParticipant(participantIds,(conference.getAmount_participant()+1),conferenceId);
                Participant participant = participantDAO.findById(participantId);
                participantDAO.updateIdConference((participant.getId_conference_participant() + (conferenceId)+","),participantId);
            }
        }
        if(error!="Message:"){
            ArrayList arrayList = new ArrayList();arrayList.add(error);arrayList.add(error);
            return jsonStringMaker.objectToJson(arrayList);
        }
        return jsonStringMaker.objectToJson(conference);
    }
}
