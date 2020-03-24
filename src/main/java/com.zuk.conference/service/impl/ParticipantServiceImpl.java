package com.zuk.conference.service.impl;

import com.zuk.conference.model.Participant;
import com.zuk.conference.service.ParticipantService;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;

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

    @Override
    public String login(Participant participant) {
        ArrayList arrayList = new ArrayList();
        String error = "Error:";
        Participant findParticipant = participantDAO.findByLogin(participant.getLogin());
        if(findParticipant==null){
            error += " Incorect Login or Password";arrayList.add(error);arrayList.add(error);
            return jsonStringMaker.arrayListToJson(arrayList);
        }
        else{
            if (DigestUtils.md5DigestAsHex((participant.getPassword()).getBytes()).equals(findParticipant.getPassword())) {
                findParticipant.setPassword("*");
                return jsonStringMaker.objectToJson(findParticipant);
            }
            else{
                error += " Incorect Login or Password1";arrayList.add(error);arrayList.add(error);
                return jsonStringMaker.arrayListToJson(arrayList);
            }
        }
    }

    @Override
    public String register(Participant participant) {
        ArrayList arrayList = new ArrayList();
        String error = "Error:";
        if(participantDAO.findByLogin(participant.getLogin())==null){
            if(participantDAO.save(participant)){
                participant.setPassword("*");
                return jsonStringMaker.objectToJson(participant);
            }
        }
        else{
            error+="This login is not available.";arrayList.add(error);arrayList.add(error);
            return jsonStringMaker.arrayListToJson(arrayList);
        }
        error+="Try later.";arrayList.add(error);arrayList.add(error);
        return jsonStringMaker.arrayListToJson(arrayList);
    }

    @Override
    public String getParticipant(int id) {
        String error = "Error:";
        Participant participant = participantDAO.findById(id);
        if(participant==null){
            ArrayList arrayList = new ArrayList();
            error+="Incorrect id";arrayList.add(error);arrayList.add(error);
            return jsonStringMaker.arrayListToJson(arrayList);
        }
        return jsonStringMaker.objectToJson(participant);
    }
}
