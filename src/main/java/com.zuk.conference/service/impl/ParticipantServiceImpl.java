package com.zuk.conference.service.impl;

import com.zuk.conference.dao.impl.ParticipantDAOImpl;
import com.zuk.conference.model.Participant;
import com.zuk.conference.service.ParticipantService;

public class ParticipantServiceImpl extends ParticipantService {


    @Override
    public boolean removeFromConference(int id,int conferenceId) {

        String conferenceIds = participantDAO.findById(id).getId_conference_participant();

        if (conferenceIds == null) {
            conferenceIds = "";
        }
        String[] strArray = conferenceIds.split(",");
        int[] intArray = new int[strArray.length];
        boolean containConference = false;

        if(!strArray[0].isEmpty()) {
            for (int i = 0; i < strArray.length; i++) {
                try {
                    intArray[i] = Integer.parseInt(strArray[i]);
                    if (intArray[i] == conferenceId) {
                        containConference = true;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        if(containConference){
            String responseIdConference="";
            for(int i=0;i<intArray.length;i++){
                if(intArray[i]!=conferenceId){
                    responseIdConference+=(intArray[i] + ",");
                }
            }
            if(participantDAO.updateIdConference(responseIdConference,id)){
                return true;
            }
        }
        return false;
    }
}
