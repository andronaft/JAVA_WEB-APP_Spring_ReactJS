package com.zuk.conference.dao.daoimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.zuk.conference.conection.ConnectionManager;
import com.zuk.conference.dao.ParticipantDAO;
import com.zuk.conference.model.Participant;
import com.zuk.conference.model.Room;
import org.h2.value.ValueBytes;
import org.springframework.util.DigestUtils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ParticipantDAOImpl implements ParticipantDAO {
    private ObjectMapper objectMapper = new ObjectMapper();


    ConnectionManager cm = new ConnectionManager();
    Connection con = cm.getConnection();
    String jsonInString="";


    @Override
    public String insertParticipant(Participant participant){
        jsonInString="";
        String error = "Error:";
        boolean isUserExists = false;
        if (con != null) {
            try {
                PreparedStatement pr,pr1;

                pr1 = con.prepareStatement("SELECT 1 from bd.PARTICIPANT where Login=?");
                pr1.setString(1,participant.getLogin());

                ResultSet resultSet = pr1.executeQuery();
                resultSet.last();
                if(resultSet.getRow()>0){
                    isUserExists = true;
                    System.out.println("resultset = 0");
                    error+="This login is not available ;";
                }

                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1));
                }
                if(isUserExists!=true){
                    pr = con.prepareStatement("insert into bd.PARTICIPANT (firstname,lastname,birthday,login,password) values (?,?,?,?,?)");
                    pr.setString(1,participant.getFirstName());
                    pr.setString(2,participant.getLastName());
                    pr.setDate(3,participant.getBirthDay());
                    pr.setString(4,participant.getLogin());
                    pr.setString(5, DigestUtils.md5DigestAsHex((participant.getPassword()).getBytes()));participant.setPassword("*");
                    pr.executeUpdate();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                error+="Please try latter 1; ";

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
            ArrayList<String> arrayList = new <String>ArrayList();
            arrayList.add(error);arrayList.add(error);

            try {
                jsonInString = objectMapper.writeValueAsString(arrayList);
            } catch (JsonProcessingException e) {
                System.out.println("error with json");
                e.printStackTrace();
            }
        }
        else{
            try {
                jsonInString = objectMapper.writeValueAsString(participant);
                System.out.println(jsonInString);
            } catch (JsonProcessingException e) {
                System.out.println("error with json");
                e.printStackTrace();
            }
        }



        return jsonInString;
    }

    @Override
    public ParticipantDAO getParticipant() {
        return null;
    }

    @Override
    public String login(Participant participant) {
        boolean isUserExists = false;
        String error = "Error:";
        if (con != null) {
            try {
                PreparedStatement pr,pr1;

                pr1 = con.prepareStatement("SELECT  * from bd.PARTICIPANT where Login=?");
                pr1.setString(1,participant.getLogin());

                ResultSet resultSet = pr1.executeQuery();

                if(resultSet.next()){
                    String password = resultSet.getString("PASSWORD");
                    if(DigestUtils.md5DigestAsHex((participant.getPassword()).getBytes()).equals(password)){
                        int id = resultSet.getInt("ID");
                        String role = resultSet.getString("ROLE");
                        String firstName = resultSet.getString("FIRSTNAME");
                        String lastName = resultSet.getString("LASTNAME");
                        participant.setId(id);participant.setRole(role);participant.setPassword("*");participant.setFirstName(firstName);participant.setLastName(lastName);
                    }
                    else{
                        error+=" Incorect Login or Password";
                    }

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
            ArrayList<String> arrayList = new <String>ArrayList();
            arrayList.add(error);arrayList.add(error);
            try {
                jsonInString = objectMapper.writeValueAsString(arrayList);
            } catch (JsonProcessingException e) {
                System.out.println("error with json");
                e.printStackTrace();
            }
        }else {
            try {
                jsonInString = objectMapper.writeValueAsString(participant);
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
            System.out.println("some problem");
        }
        System.out.println(jsonInString);

        return jsonInString;
    }

}
