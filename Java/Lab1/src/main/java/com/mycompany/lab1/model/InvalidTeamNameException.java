package com.mycompany.lab1.model;

/**
 * Thrown when a team name is invalid.
 * @author trela
 * @version 1.0
 */
public class InvalidTeamNameException extends Exception {
    public InvalidTeamNameException(String message) {
        super(message);
    }
}
