package com.mycompany.lab1.model;

import java.util.*;

/**
 * Stores unique team names and validates them.
 * Provides methods to add, remove and list teams.
 *
 * @author trela
 * @version 1.0
 */
public class TeamDatabase {
    private final Set<String> teams = new HashSet<>();

    public synchronized boolean addTeam(String name) throws InvalidTeamNameException {
        validateName(name);
        return teams.add(name.trim());
    }

    public synchronized boolean contains(String name) {
        return teams.contains(name.trim());
    }

    public synchronized List<String> getTeams() {
        return new ArrayList<>(teams);
    }

    public synchronized boolean removeTeam(String name) {
        return teams.remove(name.trim());
    }

    private void validateName(String name) throws InvalidTeamNameException {
        if (name == null || name.trim().isEmpty())
            throw new InvalidTeamNameException("Team name cannot be empty");
        if (!name.matches("[A-Za-z0-9 _\\-]+"))
            throw new InvalidTeamNameException("Invalid characters in team name");
    }
}
