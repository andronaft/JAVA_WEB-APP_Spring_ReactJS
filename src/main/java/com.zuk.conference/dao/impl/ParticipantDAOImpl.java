package com.zuk.conference.dao.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.zuk.conference.conection.ConnectionManager;
import com.zuk.conference.dao.ParticipantDAO;
import com.zuk.conference.model.Participant;

import org.springframework.util.DigestUtils;


import java.sql.*;
import java.util.ArrayList;

public class ParticipantDAOImpl extends ParticipantDAO {



    @Override
    public Participant findById(int id) {
        Participant participant = null;
        if (con != null) {
            try {
                PreparedStatement pr = getPrepareStatement("SELECT  * from PARTICIPANT where ID=?");
                pr.setInt(1, id);
                ResultSet resultSet = pr.executeQuery();

                if (resultSet.next()) {
                    participant = Participant.newBuilder()
                            .setId(resultSet.getInt("ID"))
                            .setFirstName(resultSet.getString("FIRSTNAME"))
                            .setLastName(resultSet.getString("LASTNAME"))
                            .setLogin(resultSet.getString("LOGIN"))
                            .setBirthDay(resultSet.getDate("BIRTHDAY"))
                            .setId_conference_participant(resultSet.getString("ID_CONFERENCE_PARTICIPANT"))
                            .setRole(resultSet.getString("ROLE"))
                            .build();
                }

            } catch (SQLException e) {
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return participant;
    }

    @Override
    public ArrayList findAll() {
        return null;
    }

    @Override
    public boolean update(Participant participant) {
        return false;
    }

    @Override
    public boolean updateIdConference(String idConference, int id) {
        if (con != null) {
            try {
                PreparedStatement pr = getPrepareStatement("update PARTICIPANT SET ID_CONFERENCE_PARTICIPANT = ? WHERE id=?");
                pr.setString(1, idConference);
                pr.setInt(2,id);
                pr.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("ex");

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ex");

            }
        }
        return false;
    }



    @Override
    public String insertParticipant(Participant participant){
        jsonInString="";
        String error = "Error:";
        boolean isUserExists = false;
        if (con != null) {
            try {
                PreparedStatement pr,pr1;

                pr1 = con.prepareStatement("SELECT 1 from PARTICIPANT where Login=?");
                pr1.setString(1,participant.getLogin());

                ResultSet resultSet = pr1.executeQuery();
                if(resultSet.getRow()>0){
                    isUserExists = true;
                    System.out.println("resultset = 0");
                    error+="This login is not available ;";
                }

                while (resultSet.next()) {
                   isUserExists = true;
                   error+="This login is not available ;";
                }
                if(isUserExists!=true){
                    pr = con.prepareStatement("insert into PARTICIPANT (firstname,lastname,birthday,login,password) values (?,?,?,?,?)");
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
    public String getParticipant(Participant participant)
    {jsonInString = "";

        String error = "Error:";
        if (con != null) {
            try {
                PreparedStatement pr,pr1;
                System.out.println("participant id" + participant.getId());
                pr1 = con.prepareStatement("SELECT  * from PARTICIPANT where ID=?");
                pr1.setInt(1,participant.getId());

                ResultSet resultSet = pr1.executeQuery();

                if(resultSet.next()) {
                    participant.setLogin(resultSet.getString("LOGIN"));
                    participant.setFirstName(resultSet.getString("FIRSTNAME"));
                    participant.setLastName(resultSet.getString("LASTNAME"));
                    participant.setBirthDay(resultSet.getDate("BIRTHDAY"));
                    participant.setId_conference_participant(resultSet.getString("ID_CONFERENCE_PARTICIPANT"));
                    participant.setRole(resultSet.getString("ROLE"));
                }
                if(participant.getLogin()==null){
                    error += " Incorrect Log in";
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
            System.out.println("all right");
        }
        System.out.println(jsonInString);

        return jsonInString;

}

    @Override
    public String login(Participant participant) {
        jsonInString = "";
        Participant participant1 = new Participant();
        String error = "Error:";
        if (con != null) {
            try {
                PreparedStatement pr,pr1;

                pr1 = con.prepareStatement("SELECT  * from PARTICIPANT where Login=?");
                pr1.setString(1,participant.getLogin());

                ResultSet resultSet = pr1.executeQuery();

                if(resultSet.next()) {
                    String password = resultSet.getString("PASSWORD");
                    if (DigestUtils.md5DigestAsHex((participant.getPassword()).getBytes()).equals(password)) {
                        int id = resultSet.getInt("ID");
                        String login = resultSet.getString("LOGIN");
                        String role = resultSet.getString("ROLE");
                        String firstName = resultSet.getString("FIRSTNAME");
                        String lastName = resultSet.getString("LASTNAME");
                        participant1.setId(id);
                        participant1.setLogin(login);
                        participant1.setRole(role);
                        participant1.setPassword("*");
                        participant1.setFirstName(firstName);
                        participant1.setLastName(lastName);
                    } else {
                        error += " Incorect Login or Password1";
                    }

                }
                if(participant1.getLogin()==null){
                    error += " Incorect Login or Password2";
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
                jsonInString = objectMapper.writeValueAsString(participant1);
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
    public boolean isAdmin(Participant participant) {
        boolean isAdmin = false;
        if (con != null) {
            try {
                PreparedStatement pr,pr1;

                pr1 = con.prepareStatement("SELECT  * from PARTICIPANT where ID=?");
                pr1.setInt(1,participant.getId());
                System.out.println("admin id "+participant.getId() +" pass " + participant.getPassword());
                ResultSet resultSet = pr1.executeQuery();

                if(resultSet.next()) {
                    String password = resultSet.getString("PASSWORD");
                    String role = resultSet.getString("ROLE");
                    if (DigestUtils.md5DigestAsHex((participant.getPassword()).getBytes()).equals(password)&&(role.equals("admin"))) {
                     isAdmin = true;
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isAdmin;
    }

}