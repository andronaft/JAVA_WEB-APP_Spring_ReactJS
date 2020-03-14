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

public class ConferenceDAOImpl extends ConferenceDAO {

    @Override
    public Conference findById(int id) {
        System.out.println("Conference findById  Start");
        Conference conference = null;
        if (con != null) {
            try {

                PreparedStatement pr = getPrepareStatement("SELECT * from CONFERENCE where ID = ?");
                pr.setInt(1, id);
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
    public void updateIdParticipant(String idParticipant, int amountParticipant, int id) {
        System.out.println("Conference updateIdParicipant  Start");
        if (con != null) {
            try {
                PreparedStatement pr = getPrepareStatement("Update  CONFERENCE set ID_PARTICIPANT = ?, AMOUNT_PARTICIPANT = ? where ID=?");
                pr.setString(1, idParticipant);
                pr.setInt(2,amountParticipant);
                pr.setInt(3,id);
                pr.executeUpdate();
            }
            catch (SQLException e) {
                System.out.println("SQL ex ");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("No connection");
        }
    }

    @Override
    public boolean delete(int conferenceId) {
        System.out.println("Conference deleteConference  Start");
        boolean isDelete = false;
        if (con != null) {
            ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
                try {
                    PreparedStatement pr = getPrepareStatement("DELETE from CONFERENCE where ID=?");
                    pr.setInt(1,conferenceId);
                    pr.executeUpdate();
                    isDelete = true;
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return isDelete;
    }



    @Override
    public ArrayList findAll() {
        ArrayList arrayList = new ArrayList();
        String error = "Error:";
        if (con != null) {
            try {
                PreparedStatement pr = getPrepareStatement("SELECT  * from CONFERENCE WHERE DATEE > CURRENT_DATE ORDER BY DATEE ASC, TIMEE ASC");
                ResultSet resultSet = pr.executeQuery();

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
                    System.out.println(conference.toString());
                   arrayList.add(conference);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                error+="Please try later 1; ";

            } catch (Exception e) {
                e.printStackTrace();
                error+="Please try later 2;  ";

            }
        }
        if(error!="Error:"){
            System.out.println("erro!=null");
            ArrayList arrayError= new ArrayList();
            arrayError.add(error);arrayError.add(error);
            return arrayError;
        }else {
            return arrayList;
        }
    }

    @Override
    public String createConf(Participant admin,Conference conference) {
        jsonInString="";
        String message = "Message:";
        if (con != null) {
            ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
            if (participantDAO.isAdmin(admin)) {
                RoomDAOImpl roomDAO = new RoomDAOImpl();
                Room room = roomDAO.getRoomFromId(conference.getId_room());

                try {
                    PreparedStatement pr1;

                    pr1 = con.prepareStatement("insert into CONFERENCE (NAME,ID_ROOM,NAME_ROOM,CAPACITY_ROOM,AMOUNT_PARTICIPANT,DATEE,TIMEE) values (?,?,?,?,?,?,?)");
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
            if (participantDAO.isAdmin(admin)) {


                try {
                    PreparedStatement pr, pr1;
                    System.out.println("try to do exequt");
                    pr1 = con.prepareStatement("SELECT * from CONFERENCE where id = ?");
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

                            pr = con.prepareStatement("Update  CONFERENCE set ID_PARTICIPANT = ?, AMOUNT_PARTICIPANT = ? where ID=?");
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
            if (participantDAO.isAdmin(participant)) {

                try {
                    PreparedStatement pr1;

                    pr1 = con.prepareStatement("UPDATE CONFERENCE SET DATEE = ? , TIMEE= ? WHERE ID = ?;");
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
