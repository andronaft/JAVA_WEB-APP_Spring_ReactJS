package com.zuk.conference.dao;

import com.zuk.conference.model.Conference;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ConferenceDAO {

    Conference findById(int id);

    /** Same as {@link #findById(int)} but locks the row until the end of the transaction. */
    Conference findByIdForUpdate(int id);

    /** Conferences from today on, ordered by date and time. */
    List<Conference> findUpcoming();

    void save(Conference conference);

    boolean updateIdParticipant(String idParticipant, int amountParticipant, int id);

    boolean updateDateAndTime(int id, LocalDate datee, LocalTime timee);

    boolean delete(int conferenceId);
}
