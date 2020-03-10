package com.zuk.conference.dao.daoimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuk.conference.conection.ConnectionManager;
import com.zuk.conference.dao.ConferenceDAO;
import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;
import com.zuk.conference.model.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class ConferenseAndAmountJSON {
    private ArrayList<Conference> arrayList;
    private int amount;

    public ArrayList<Conference> getArrayList() {
        return arrayList;
    }

    public int getAmount() {
        return amount;
    }

    public ConferenseAndAmountJSON() {
    }

    public void setArrayList(ArrayList<Conference> arrayList) {
        this.arrayList = arrayList;
        this.amount = arrayList.size();
    }
}

public class ConferenceDAOImpl extends ConferenceDAO {

    @Override
    public Conference findById(int conferenceId) {
        System.out.println("Conference findById  Start");
        Conference conference = null;
        if (con != null) {
            try {

                PreparedStatement pr = getPrepareStatement("SELECT * from bd.CONFERENCE where ID = ?");
                pr.setInt(1, conferenceId);
                ResultSet resultSet = pr.executeQuery();

                if (resultSet.next()) {
                    conference = Conference.newBuilder()
                            .setAmount_participant(resultSet.getInt("AMOUNT_PARTICIPANT"))
                            .setCapacity_room(resultSet.getInt("CAPACITY_ROOM"))
                            .setId(resultSet.getInt("ID"))
                            .setName(resultSet.getString("NAME"))
                            .setId_room(resultSet.getInt("ID_ROOM"))
                            .setName_room(resultSet.getString("NAME_ROOM"))
                            .setId_participant(resultSet.getString("ID_PARTICIPANT"))
                            .setDatee(resultSet.getDate("DATEE"))
                            .setTimee(resultSet.getTime("TIMEE"))
                            .build();
                }
            } catch (SQLException e) {
                System.out.println("SQL ex ");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("No connection");
        }

        return conference;
    }

    @Override
    public String joinNewParticipant(Participant participant, Conference conference) {
        String error = "";
        System.out.println("start join in conf");
        if (con != null) {
                try {
                    PreparedStatement pr, pr1;
                    System.out.println("try to do exequt + conference id "+ conference.getId());
                    pr1 = con.prepareStatement("SELECT * from bd.CONFERENCE where ID = ?");
                    pr1.setInt(1,conference.getId());

                    ResultSet resultSet = pr1.executeQuery();
                    String participantConferencsIds = "";

                    if(resultSet.next()) {
                        conference = Conference.newBuilder()
                                .setAmount_participant(resultSet.getInt("AMOUNT_PARTICIPANT"))
                                .setCapacity_room(resultSet.getInt("CAPACITY_ROOM"))
                                .build();
//                        conference.setAmount_participant(resultSet.getInt("AMOUNT_PARTICIPANT"));
//                        conference.setCapacity_room(resultSet.getInt("CAPACITY_ROOM"));
                        participantConferencsIds = resultSet.getString("ID_PARTICIPANT");
                    }
                    System.out.println("participant Conferences Ids old" + participantConferencsIds);
                    if((conference.getAmount_participant()) < (conference.getCapacity_room())) {

                        if (participantConferencsIds == null) {
                            participantConferencsIds = "";
                        }

                        String[] strArray = participantConferencsIds.split(",");
                        int[] intArray = new int[strArray.length];

                        boolean containParticipant = false;
                        if(participantConferencsIds!="") {
                            for (int i = 0; i < strArray.length; i++) {
                                try {
                                    intArray[i] = Integer.parseInt(strArray[i]);
                                    if (intArray[i] == participant.getId()) {
                                        containParticipant = true;
                                    }
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        if(!containParticipant) {
                            participantConferencsIds += (participant.getId() + ",");
                            System.out.println("prticipant Conference IDS new "+ participantConferencsIds);

                            pr = con.prepareStatement("Update  bd.CONFERENCE set ID_PARTICIPANT = ?, AMOUNT_PARTICIPANT = ? where ID=?");
                            pr.setString(1, participantConferencsIds);
                            System.out.println(" parcipiant Conf" + participantConferencsIds);
                            pr.setInt(2, (conference.getAmount_participant()+1));
                            System.out.println("amount " + (resultSet.getInt("AMOUNT_PARTICIPANT") + 1));
                            pr.setInt(3, conference.getId());
                            System.out.println("conferemse id " + conference.getId());

                            pr.executeUpdate();
                            error += "Join participant from Conference";
                        }
                        else {
                            error += "You already consist in this conference";
                        }
                    }
                    else {
                        error+="There was no place";
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    error += "try later ";

                } catch (Exception e) {
                    e.printStackTrace();
                    error += "try later  ";

                } finally {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        error += " try later ";
                        e.printStackTrace();
                    }
                }

        }
        return error;
    }


    @Override
    public String cancelConferece(Participant admin, Conference conference) {
        jsonInString="";
        String message = "Message:";
        if (con != null) {
            ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
            if (participantDAO.isadmin(admin)) {

                try {
                    PreparedStatement pr1;

                    pr1 = con.prepareStatement("DELETE from bd.CONFERENCE where ID=?");
                    pr1.setInt(1,conference.getId());
                    pr1.executeUpdate();
                    message+=("Conference was delete");

                } catch (SQLException e) {
                    e.printStackTrace();
                    message += "try later ";

                } catch (Exception e) {
                    e.printStackTrace();
                    message += "try later  ";

                } finally {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        message += " try later ";
                        e.printStackTrace();
                    }
                }
            }
            else {
                message+=" incorrect password ";
            }
        }
        if(message!="Message:"){
            System.out.println("erro!=null");
            ArrayList<String> arrayListe= new <String>ArrayList();
            arrayListe.add(message);arrayListe.add(message);
            try {
                jsonInString = objectMapper.writeValueAsString(arrayListe);
            } catch (JsonProcessingException e) {
                System.out.println("error with json");
                e.printStackTrace();
            }
        }else {
            message+="some trouble ";
            ArrayList<String> arrayListe= new <String>ArrayList();
            arrayListe.add(message);arrayListe.add(message);
            try {
                jsonInString = objectMapper.writeValueAsString(arrayListe);
            } catch (JsonProcessingException e) {
                System.out.println("error with json");
                e.printStackTrace();
            }
        }
        System.out.println(jsonInString);

        return jsonInString;
    }

    @Override
    public String getAllConference() {
        jsonInString="";
        ArrayList<Conference> arrayList = new ArrayList<Conference>();
        String error = "Error:";
        if (con != null) {
            try {
                PreparedStatement pr,pr1;

                pr1 = con.prepareStatement("SELECT  * from bd.CONFERENCE WHERE DATEE > CURRENT_DATE() ORDER BY DATEE ASC, TIMEE ASC");

                ResultSet resultSet = pr1.executeQuery();


                while (resultSet.next()){
                    Conference conference = Conference.newBuilder()
                            .setId(resultSet.getInt("ID"))
                            .setName(resultSet.getString("NAME"))
                            .setId_room(resultSet.getInt("ID_ROOM"))
                            .setName_room(resultSet.getString("NAME_ROOM"))
                            .setId_participant(resultSet.getString("id_PARTICIPANT"))
                            .setCapacity_room(resultSet.getInt("CAPACITY_ROOM"))
                            .setAmount_participant(resultSet.getInt("AMOUNT_PARTICIPANT"))
                            .setDatee(resultSet.getDate("DATEE"))
                            .setTimee(resultSet.getTime("TIMEE"))
                            .build();
                   arrayList.add(conference);
                }



            } catch (SQLException e) {
                e.printStackTrace();
                error+="Please try later 1; ";

            } catch (Exception e) {
                e.printStackTrace();
                error+="Please try later 2;  ";

            }finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    error+="please try later 3; ";
                    e.printStackTrace();
                }
            }
        }
        if(error!="Error:"){
            System.out.println("erro!=null");
            ArrayList<String> arrayListe= new <String>ArrayList();
            arrayListe.add(error);arrayListe.add(error);
            try {
                jsonInString = objectMapper.writeValueAsString(arrayListe);
            } catch (JsonProcessingException e) {
                System.out.println("error with json");
                e.printStackTrace();
            }
        }else {
            try {
                ConferenseAndAmountJSON json = new ConferenseAndAmountJSON();
                json.setArrayList(arrayList);
                jsonInString = objectMapper.writeValueAsString(json);
                System.out.println(jsonInString);
            } catch (JsonProcessingException e) {
                System.out.println("error with json");
                e.printStackTrace();
            }
        }
        if(jsonInString.equals("")){
            System.out.println("jsonempty");
        }
        else {
            System.out.println("all right");
        }
        System.out.println(jsonInString);

        return jsonInString;
    }

    @Override
    public String createConf(Participant admin,Conference conference) {
        jsonInString="";
        String message = "Message:";
        if (con != null) {
            ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
            if (participantDAO.isadmin(admin)) {
                RoomDAOImpl roomDAO = new RoomDAOImpl();
                Room room = roomDAO.getRoomFromId(conference.getId_room());

                try {
                    PreparedStatement pr1;

                    pr1 = con.prepareStatement("insert into bd.CONFERENCE (NAME,ID_ROOM,NAME_ROOM,CAPACITY_ROOM,AMOUNT_PARTICIPANT,DATEE,TIMEE) values (?,?,?,?,?,?,?)");
                    pr1.setString(1,conference.getName());
                    pr1.setInt(2,room.getId());
                    pr1.setString(3,room.getName());
                    pr1.setInt(4,(room.getFirstFloorCapacity()+room.getSecondFloorCapacity()));
                    pr1.setInt(5,conference.getAmount_participant());
                    pr1.setDate(6,conference.getDatee());
                    pr1.setTime(7,conference.getTimee());
                    pr1.executeUpdate();
                    message+=("Conference was created" + conference.getName()+room.getId()+room.getName()+String.valueOf(room.getFirstFloorCapacity()+room.getSecondFloorCapacity())+conference.getAmount_participant()+ conference.getDatee()+conference.getTimee());

                } catch (SQLException e) {
                    e.printStackTrace();
                    message += "check room id and try later ";

                } catch (Exception e) {
                    e.printStackTrace();
                    message += "check room id and try later  ";

                } finally {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        message += " try later ";
                        e.printStackTrace();
                    }
                }
            }
            else {
                message+=" incorrect password ";
            }
        }
        if(message!="Message:"){
            System.out.println("erro!=null");
            ArrayList<String> arrayListe= new <String>ArrayList();
            arrayListe.add(message);arrayListe.add(message);
            try {
                jsonInString = objectMapper.writeValueAsString(arrayListe);
            } catch (JsonProcessingException e) {
                System.out.println("error with json");
                e.printStackTrace();
            }
        }else {
            message+="some trouble ";
            ArrayList<String> arrayListe= new <String>ArrayList();
            arrayListe.add(message);arrayListe.add(message);
            try {
                jsonInString = objectMapper.writeValueAsString(arrayListe);
            } catch (JsonProcessingException e) {
                System.out.println("error with json");
                e.printStackTrace();
            }
        }
        System.out.println(jsonInString);

        return jsonInString;
    }

    @Override
    public String removeParticipant(Participant admin, Participant participant, Conference conference) {
        jsonInString="";
        String error = "Message:";
        System.out.println("statr remove in conf");
        if (con != null) {
            ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
            if (participantDAO.isadmin(admin)) {


                try {
                    PreparedStatement pr, pr1;
                    System.out.println("try to do exequt");
                    pr1 = con.prepareStatement("SELECT * from bd.CONFERENCE where id = ?");
                    pr1.setInt(1,conference.getId());

                    ResultSet resultSet = pr1.executeQuery();

                    String participantConferencsIds="";
                    if(resultSet.next()) {

                        participantConferencsIds = resultSet.getString("ID_PARTICIPANT");
                        System.out.println("make participantCOnfids");
                    }

                    System.out.println(participantConferencsIds+"conferenceDao");
                    String[] strArray = participantConferencsIds.split(",");
                    int[] intArray = new int[strArray.length];

                    boolean containParticipant = false;
                    for(int i = 0; i < strArray.length; i++) {
                        intArray[i] = Integer.parseInt(strArray[i]);
                        if(intArray[i]==participant.getId()){
                            containParticipant = true;
                        }
                        System.out.println(strArray[i]);
                        System.out.println(Integer.parseInt(strArray[i]));
                        System.out.println(Integer.valueOf(strArray[i]));
                    }

                    if(containParticipant){
                        ParticipantDAOImpl participantDAO1 = new ParticipantDAOImpl();
                        if(participantDAO1.removeFromConference(participant,conference.getId())){

                            String responseIdConference="";
                            for(int i=0;i<intArray.length;i++){
                                if(intArray[i]!=participant.getId()){
                                    responseIdConference+=(intArray[i] + ",");
                                }
                            }
                            System.out.println("conf resp" + responseIdConference);

                            pr = con.prepareStatement("Update  bd.CONFERENCE set ID_PARTICIPANT = ?, AMOUNT_PARTICIPANT = ? where ID=?");
                            pr.setString(1,responseIdConference);
                            pr.setInt(2,(intArray.length-1));
                            pr.setInt(3,conference.getId());

                            pr.executeUpdate();
                            error+="Remove participant from Conference";

                        }else{
                            error+="can`t remove part from conf in part bd";
                        }

                    }
                    else{
                        error+=(participant.getId() + " participant doesn`t consist in this Conference");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    error += "check room id and try later ";

                } catch (Exception e) {
                    e.printStackTrace();
                    error += "check room id and try later  ";

                } finally {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        error += " try later ";
                        e.printStackTrace();
                    }
                }
            }
            else {
                error+=" incorrect password ";
            }
        }
        if(error!="Message:"){
            System.out.println("erro!=null");
            ArrayList<String> arrayListe= new <String>ArrayList();
            arrayListe.add(error);arrayListe.add(error);
            try {
                jsonInString = objectMapper.writeValueAsString(arrayListe);
            } catch (JsonProcessingException e) {
                System.out.println("error with json");
                e.printStackTrace();
            }
        }else {
            error+="some trouble ";
            ArrayList<String> arrayListe= new <String>ArrayList();
            arrayListe.add(error);arrayListe.add(error);
            try {
                jsonInString = objectMapper.writeValueAsString(arrayListe);
            } catch (JsonProcessingException e) {
                System.out.println("error with json");
                e.printStackTrace();
            }
        }
        System.out.println(jsonInString);

        return jsonInString;
    }

    @Override
    public String changeTime(Participant participant, Conference conference) {//TODO used functionality ConferenceDao
        jsonInString="";
        String message = "Message:";
        if (con != null) {
            ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
            System.out.println("gg");
            if (participantDAO.isadmin(participant)) {

                try {
                    PreparedStatement pr1;

                    pr1 = con.prepareStatement("UPDATE BD.CONFERENCE SET DATEE = ? , TIMEE= ? WHERE ID = ?;");
                    pr1.setDate(1,conference.getDatee());
                    pr1.setTime(2,conference.getTimee());
                    pr1.setInt(3,conference.getId());
                    pr1.executeUpdate();
                    message+=("Conference time was changed");

                } catch (SQLException e) {
                    e.printStackTrace();
                    message += "try later ";

                } catch (Exception e) {
                    e.printStackTrace();
                    message += "try later  ";

                } finally {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        message += " try later ";
                        e.printStackTrace();
                    }
                }
            }
            else {
                message+=" incorrect password ";
            }
        }
        if(message!="Message:"){
            System.out.println("erro!=null");
            ArrayList<String> arrayListe= new <String>ArrayList();
            arrayListe.add(message);arrayListe.add(message);
            try {
                jsonInString = objectMapper.writeValueAsString(arrayListe);
            } catch (JsonProcessingException e) {
                System.out.println("error with json");
                e.printStackTrace();
            }
        }else {
            message+="some trouble ";
            ArrayList<String> arrayListe= new <String>ArrayList();
            arrayListe.add(message);arrayListe.add(message);
            try {
                jsonInString = objectMapper.writeValueAsString(arrayListe);
            } catch (JsonProcessingException e) {
                System.out.println("error with json");
                e.printStackTrace();
            }
        }
        System.out.println(jsonInString);

        return jsonInString;
    }


}
