package com.zuk.conference.service.impl;

import com.zuk.conference.auxiliary.PasswordHasher;
import com.zuk.conference.dao.ParticipantDAO;
import com.zuk.conference.model.Participant;
import com.zuk.conference.service.ConferenceException;
import com.zuk.conference.service.ParticipantService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantDAO participantDAO;
    private final PasswordHasher passwordHasher;

    public ParticipantServiceImpl(ParticipantDAO participantDAO, PasswordHasher passwordHasher) {
        this.participantDAO = participantDAO;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public Participant login(String login, String password) {
        Participant participant = participantDAO.findByLogin(login);
        if (participant == null || !passwordHasher.matches(password, participant.getPassword())) {
            throw new ConferenceException("Incorrect login or password");
        }
        // Accounts created by the old version use unsalted MD5, re-hash them with BCrypt.
        if (passwordHasher.isLegacy(participant.getPassword()) && passwordHasher.isAcceptableLength(password)) {
            participantDAO.updatePassword(participant.getId(), passwordHasher.hash(password));
        }
        return participant;
    }

    @Override
    public Participant register(Participant participant) {
        if (isBlank(participant.getLogin()) || isBlank(participant.getPassword())
                || isBlank(participant.getFirstName()) || isBlank(participant.getLastName())
                || participant.getBirthDay() == null) {
            throw new ConferenceException("All fields are required");
        }
        if (!passwordHasher.isAcceptableLength(participant.getPassword())) {
            throw new ConferenceException("Password is too long");
        }
        if (participantDAO.findByLogin(participant.getLogin()) != null) {
            throw new ConferenceException("This login is not available");
        }
        participant.setPassword(passwordHasher.hash(participant.getPassword()));
        int id;
        try {
            id = participantDAO.save(participant);
        } catch (DuplicateKeyException e) {
            throw new ConferenceException("This login is not available");
        }
        return participantDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Participant getParticipant(int id) {
        Participant participant = participantDAO.findById(id);
        if (participant == null) {
            throw new ConferenceException("Incorrect id");
        }
        return participant;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isAdmin(int id, String password) {
        Participant participant = participantDAO.findById(id);
        return participant != null
                && participant.isAdmin()
                && passwordHasher.matches(password, participant.getPassword());
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
