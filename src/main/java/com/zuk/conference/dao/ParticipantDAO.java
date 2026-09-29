package com.zuk.conference.dao;

import com.zuk.conference.model.Participant;

public interface ParticipantDAO {

    Participant findById(int id);

    /** Same as {@link #findById(int)} but locks the row until the end of the transaction. */
    Participant findByIdForUpdate(int id);

    Participant findByLogin(String login);

    /** Inserts the participant (password must already be hashed) and returns the generated id. */
    int save(Participant participant);

    boolean updateIdConference(String idConference, int id);

    boolean updatePassword(int id, String passwordHash);
}
