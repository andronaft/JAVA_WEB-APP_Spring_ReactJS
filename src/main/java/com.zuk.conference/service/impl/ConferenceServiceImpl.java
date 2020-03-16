package com.zuk.conference.service.impl;


import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;
import com.zuk.conference.model.Room;
import com.zuk.conference.service.ConferenceService;


import java.util.ArrayList;


public class ConferenceServiceImpl  extends ConferenceService {


    @Override
    public String addNewParticipant(int participantId, int conferenceId) {
        String error = "Message:";
        Conference conference = conferenceDAO.findById(conferenceId);

        if(conference.getAmount_participant()<conference.getCapacity_room()){
            String participantIds = conference.getId_participant();

            if (participantIds == null) {
                participantIds = "";
            }

            String[] strArray = participantIds.split(",");
            int[] intArray = new int[strArray.length];
            boolean containParticipant = false;

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
                if(!conferenceDAO.updateIdParticipant(participantIds,(conference.getAmount_participant()+1),conferenceId)){
                    error+="Try later.";
                    ArrayList arrayList = new ArrayList();arrayList.add(error);arrayList.add(error);
                    return jsonStringMaker.objectToJson(arrayList);
                }
                Participant participant = participantDAO.findById(participantId);
                if(!participantDAO.updateIdConference((participant.getId_conference_participant() + (conferenceId)+","),participantId)){
                    error+="Try later.";
                    ArrayList arrayList = new ArrayList();arrayList.add(error);arrayList.add(error);
                    return jsonStringMaker.objectToJson(arrayList);
                }
            }
        }
        if(error!="Message:"){
            ArrayList arrayList = new ArrayList();arrayList.add(error);arrayList.add(error);
            return jsonStringMaker.objectToJson(arrayList);
        }
        return jsonStringMaker.objectToJson(conference);
    }

    @Override
    public String removeParticipant(Participant admin, int conferenceId, int participantId) {
        ArrayList arrayList = new ArrayList();
        message = "Message:";
        if(participantDAO.isAdmin(admin)){
            Conference conference = conferenceDAO.findById(conferenceId);
            String participantIds = conference.getId_participant();

            if (participantIds == null) {
                participantIds = "";
            }
            String[] strArray = participantIds.split(",");
            int[] intArray = new int[strArray.length];
            boolean containParticipant = false;

            if(!strArray[0].isEmpty()) {
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
            if(containParticipant){
                String responseIdParticipant="";
                for(int i=0;i<intArray.length;i++){
                    if(intArray[i]!=participantId){
                        responseIdParticipant+=(intArray[i] + ",");
                    }
                }
                conference.setId_participant(responseIdParticipant);
                conference.setAmount_participant((conference.getAmount_participant()-1));
                if(conferenceDAO.update(conference)){
                    if(participantService.removeFromConference(participantId,conferenceId)){
                        message+="Participant removed";
                        arrayList.add(message);arrayList.add(message);
                        return jsonStringMaker.objectToJson(arrayList);
                    }

                }
            }
            else {
                message+="Participant is not registered at the conference";arrayList.add(message);arrayList.add(message);
                return jsonStringMaker.objectToJson(arrayList);
            }


        }else {
            message+="Incorrect admin/meneger password";arrayList.add(message);arrayList.add(message);
            return jsonStringMaker.objectToJson(arrayList);
        }
        message+="Try later.";
        arrayList.add(message);arrayList.add(message);
        return jsonStringMaker.arrayListToJson(arrayList);
    }

    @Override
    public String changeDateAndTime(Participant admin, Conference conference) {
        ArrayList arrayList = new ArrayList();
        message = "Message:";
        if(participantDAO.isAdmin(admin)){
            if(conferenceDAO.updateDateAndTime(conference)){
                message+="Conference Time was change";arrayList.add(message);arrayList.add(message);
                return(jsonStringMaker.objectToJson(arrayList));
            }
            else{
                message+="Try later";arrayList.add(message);arrayList.add(message);
                return jsonStringMaker.objectToJson(arrayList);
            }
        }else {
            message+="Incorrect admin/manager password";arrayList.add(message);arrayList.add(message);
            return jsonStringMaker.objectToJson(arrayList);
        }
    }

    @Override
    public String cancel(Participant admin,int conferenceId) {
        ArrayList arrayList = new ArrayList();
        message = "Message:";
        if(participantDAO.isAdmin(admin)){
            if(conferenceDAO.delete(conferenceId)){
                message+="Conference was cancel";arrayList.add(message);arrayList.add(message);
                return(jsonStringMaker.objectToJson(arrayList));
            }
            else{
                message+="Try later";arrayList.add(message);arrayList.add(message);
                return jsonStringMaker.objectToJson(arrayList);
            }
        }else {
            message+="Incorrect admin/manager password";arrayList.add(message);arrayList.add(message);
            return jsonStringMaker.objectToJson(arrayList);
        }
    }

    @Override
    public String getAll() {
        return jsonStringMaker.arrayListToConferenceJson(conferenceDAO.findAll());
    }

    @Override
    public String create(Participant admin,Conference conference) {
        ArrayList arrayList = new ArrayList();
        message = "Message:";
        if(participantDAO.isAdmin(admin)){
            Room room = roomDAO.findById(conference.getId_room());
            if(room!=null) {
                conference.setName_room(room.getName());
                conference.setCapacity_room((room.getFirstFloorCapacity()+room.getSecondFloorCapacity()));
                if (conferenceDAO.save(conference)) {
                    message += "Conference was create";
                    arrayList.add(message);
                    arrayList.add(message);
                    return (jsonStringMaker.objectToJson(arrayList));
                } else {
                    message += "Try later";
                    arrayList.add(message);
                    arrayList.add(message);
                    return jsonStringMaker.objectToJson(arrayList);
                }
            }
            else {
                message+="Incorrect id room";arrayList.add(message);arrayList.add(message);
                return jsonStringMaker.objectToJson(arrayList);
            }
        }else {
            message+="Incorrect admin/meneger password";arrayList.add(message);arrayList.add(message);
            return jsonStringMaker.objectToJson(arrayList);
        }
    }

}
