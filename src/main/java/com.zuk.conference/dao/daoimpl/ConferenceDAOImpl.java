package com.zuk.conference.dao.daoimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuk.conference.conection.ConnectionManager;
import com.zuk.conference.dao.ConferenceDAO;
import com.zuk.conference.model.Conference;
import com.zuk.conference.model.Participant;
import com.zuk.conference.model.Room;
import org.springframework.util.DigestUtils;

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

    public void setArrayList(ArrayList<Conference> arrayList) {
        this.arrayList = arrayList;
        this.amount = arrayList.size();
    }
}

public class ConferenceDAOImpl implements ConferenceDAO {

    private ObjectMapper objectMapper = new ObjectMapper();
    ConnectionManager cm = new ConnectionManager();
    Connection con = cm.getConnection();
    String jsonInString = "";

    @Override
    public void insertConference() {
        
    }

    @Override
    public void deleteConferece() {

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
                    Conference conference = new Conference();
                   conference.setId(resultSet.getInt("ID"));
                   conference.setName(resultSet.getString("NAME"));
                   conference.setId_room(resultSet.getInt("ID_ROOM"));
                   conference.setName_room(resultSet.getString("NAME_ROOM"));
                   conference.setId_participant(resultSet.getString("id_PARTICIPANT"));
                   conference.setCapacity_room(resultSet.getInt("CAPACITY_ROOM"));
                   conference.setAmount_participant(resultSet.getInt("AMOUNT_PARTICIPANT"));
                   conference.setDatee(resultSet.getDate("DATEE"));
                   conference.setTimee(resultSet.getTime("TIMEE"));
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
    public String createConf(Participant participant,Conference conference) {
        jsonInString="";
        String error = "Message:";
        if (con != null) {
            ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
            if (participantDAO.isadmin(participant)) {
                RoomDAOImpl roomDAO = new RoomDAOImpl();
                Room room = roomDAO.getRoomFromId(conference.getId_room());

                try {
                    PreparedStatement pr, pr1;

                    pr1 = con.prepareStatement("insert into bd.CONFERENCE (NAME,ID_ROOM,NAME_ROOM,CAPACITY_ROOM,AMOUNT_PARTICIPANT,DATEE,TIMEE) values (?,?,?,?,?,?,?)");
                    pr1.setString(1,conference.getName());
                    pr1.setInt(2,room.getId());
                    pr1.setString(3,room.getName());
                    pr1.setInt(4,(room.getFirstFloorCapacity()+room.getSecondFloorCapacity()));
                    pr1.setInt(5,conference.getAmount_participant());
                    pr1.setDate(6,conference.getDatee());
                    pr1.setTime(7,conference.getTimee());
                    pr1.executeUpdate();
                    error+=("Conference was created" + conference.getName()+room.getId()+room.getName()+String.valueOf(room.getFirstFloorCapacity()+room.getSecondFloorCapacity())+conference.getAmount_participant()+ conference.getDatee()+conference.getTimee());

                    //1asd4702010-10-1012:30:00
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
    public void updateConferance() {

    }
}
