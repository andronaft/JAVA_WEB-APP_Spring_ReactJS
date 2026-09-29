package com.zuk.conference.service;

/** A business error whose message is shown to the user as is. */
public class ConferenceException extends RuntimeException {

    public ConferenceException(String message) {
        super(message);
    }
}
