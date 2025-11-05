package com.mycompany.lab1.view;

import com.mycompany.lab1.model.Match;
import java.util.*;

/**
 * Console-based view for displaying matches, teams and errors.
 * No internal state.
 *
 * @author trela
 * @version 1.0
 */
public class MatchView {

    public void showMenu() {
        System.out.println("\n==== MATCH MANAGER ====");
        System.out.println("1. Add match");
        System.out.println("2. Show all matches");
        System.out.println("3. Remove match");
        System.out.println("4. Show all teams");
        System.out.println("5. Remove team");
        System.out.println("6. Show ranking");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    public void displayMatch(Match m) {
        System.out.println(m);
        System.out.println("Winner: " + m.getWinner());
    }

    public void displayTeams(List<String> teams) {
        System.out.println("\n=== TEAMS ===");
        if (teams.isEmpty()) System.out.println("(none)");
        else teams.forEach(t -> System.out.println("- " + t));
    }

    public void displayMatches(List<Match> matches) {
        System.out.println("\n=== MATCHES ===");
        if (matches.isEmpty()) System.out.println("(none)");
        else {
            int i = 0;
            for (Match m : matches) System.out.println((i++) + ": " + m);
        }
    }

    public void displayRanking(List<Map.Entry<String, Integer>> ranking) {
        System.out.println("\n=== RANKING ===");
        if (ranking.isEmpty()) System.out.println("(no wins yet)");
        else for (Map.Entry<String, Integer> e : ranking)
            System.out.println(e.getKey() + " - " + e.getValue() + " wins");
    }

    public void displayError(String msg) {
        System.err.println("ERROR: " + msg);
    }

    public void displayInfo(String msg) {
        System.out.println(msg);
    }
}
