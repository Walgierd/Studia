package com.mycompany.lab1.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Stores all matches and allows computing rankings.
 * No CSV or external storage used.
 *
 * @author trela
 * @version 1.0
 */
public class MatchDatabase {
    private final List<Match> matches = new ArrayList<>();

    public void addMatch(Match m) {
        matches.add(m);
    }

    public List<Match> getMatches() {
        return Collections.unmodifiableList(matches);
    }

    public boolean removeMatch(int index) {
        if (index < 0 || index >= matches.size()) return false;
        matches.remove(index);
        return true;
    }

    public List<Map.Entry<String, Integer>> getWinsRanking() {
        Map<String, Integer> wins = new HashMap<>();
        for (Match m : matches) {
            String winner = m.getWinner();
            if (!"Draw".equals(winner))
                wins.merge(winner, 1, Integer::sum);
        }
        return wins.entrySet().stream()
                .sorted((a,b)->b.getValue().compareTo(a.getValue()))
                .collect(Collectors.toList());
    }
}
