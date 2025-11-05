package com.mycompany.lab1.model;

/**
 * Immutable representation of a match between two teams and their scores.
 * Full encapsulation and clean OOP style.
 *
 * @author trela
 * @version 1.0
 */
public final class Match {
    private final String teamA;
    private final String teamB;
    private final int scoreA;
    private final int scoreB;

    /**
     * Creates a new Match instance.
     * @param teamA name of team A (non-null)
     * @param teamB name of team B (non-null)
     * @param scoreA score of team A
     * @param scoreB score of team B
     * @throws NullPointerException if team names are null
     */
    public Match(String teamA, String teamB, int scoreA, int scoreB) {
        if (teamA == null || teamB == null)
            throw new NullPointerException("Team names must not be null");
        this.teamA = teamA;
        this.teamB = teamB;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
    }

    public String getTeamA() { return teamA; }
    public String getTeamB() { return teamB; }
    public int getScoreA() { return scoreA; }
    public int getScoreB() { return scoreB; }

    /**
     * Returns the winner's name or "Draw".
     * @return team name or "Draw"
     */
    public String getWinner() {
        if (scoreA > scoreB) return teamA;
        if (scoreB > scoreA) return teamB;
        return "Draw";
    }

    @Override
    public String toString() {
        return teamA + " vs " + teamB + " -> " + scoreA + ":" + scoreB;
    }
}
