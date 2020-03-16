package com.zuk.conference.dao.impl;


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
                            .setPassword(resultSet.getString("PASSWORD"))
                            .setBirthDay(resultSet.getDate("BIRTHDAY"))
                            .setId_conference_participant(resultSet.getString("ID_CONFERENCE_PARTICIPANT"))
                            .setRole(resultSet.getString("ROLE"))
                            .build();
                    return participant;
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
    public Participant findByLogin(String login) {
        Participant participant = null;
        if (con != null) {
            try {
                PreparedStatement pr = getPrepareStatement("SELECT  * from PARTICIPANT where Login=?");
                pr.setString(1, login);
                ResultSet resultSet = pr.executeQuery();
                if (resultSet.next()) {
                    participant = Participant.newBuilder()
                            .setId(resultSet.getInt("ID"))
                            .setFirstName(resultSet.getString("FIRSTNAME"))
                            .setLastName(resultSet.getString("LASTNAME"))
                            .setLogin(resultSet.getString("LOGIN"))
                            .setPassword(resultSet.getString("PASSWORD"))
                            .setBirthDay(resultSet.getDate("BIRTHDAY"))
                            .setId_conference_participant(resultSet.getString("ID_CONFERENCE_PARTICIPANT"))
                            .setRole(resultSet.getString("ROLE"))
                            .build();
                    return participant;
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
    }//TODO doit

    @Override
    public boolean update(Participant participant) {
        return false;
    }//TODO doit

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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean save(Participant participant) {
        if (con != null) {
            try {
                PreparedStatement pr = getPrepareStatement("insert into PARTICIPANT (FIRSTNAME,LASTNAME,LOGIN,PASSWORD,ROLE,BIRTHDAY) values (?,?,?,?,?,?)");
                pr.setString(1,participant.getFirstName());
                pr.setString(2,participant.getLastName());
                pr.setString(3,participant.getLogin());
                pr.setString(4,DigestUtils.md5DigestAsHex((participant.getPassword()).getBytes()));
                pr.setString(5,participant.getRole());
                pr.setDate(6,participant.getBirthDay());
                pr.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
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
