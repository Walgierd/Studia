package com.mycompany.lab1.controller;

import com.mycompany.lab1.model.*;
import com.mycompany.lab1.view.MatchView;
import java.util.*;

/**
 * Controller class connecting the model and the view layers.
 * <p>
 * Responsible for coordinating user interaction, validating input,
 * handling exceptions, and updating the model and view accordingly.
 * This class manages all console interactions such as adding and
 * removing matches or teams, and viewing statistics.
 * </p>
 *
 * @author Olivier
 * @version 1.1
 */
public class MatchController {

    /** Database storing all matches. */
    private final MatchDatabase matchDb;

    /** Database storing all teams. */
    private final TeamDatabase teamDb;

    /** View responsible for displaying information to the user. */
    private final MatchView view;

    /** Scanner for console input. */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs the controller and connects it with the model and the view.
     *
     * @param matchDb reference to match database
     * @param teamDb  reference to team database
     * @param view    reference to the view object
     */
    public MatchController(MatchDatabase matchDb, TeamDatabase teamDb, MatchView view) {
        this.matchDb = matchDb;
        this.teamDb = teamDb;
        this.view = view;
    }

    /**
     * Starts the main application loop.
     * Displays the menu and processes user-selected options.
     */
    public void start() {
        int choice;
        do {
            view.showMenu();
            choice = readIntWithPrompt("Choose option: ");
            switch (choice) {
                case 1 -> addMatchInteractive();
                case 2 -> view.displayMatches(matchDb.getMatches());
                case 3 -> removeMatch();
                case 4 -> view.displayTeams(teamDb.getTeams());
                case 5 -> removeTeam();
                case 6 -> view.displayRanking(matchDb.getWinsRanking());
                case 0 -> view.displayInfo("Exiting...");
                default -> view.displayError("Invalid option.");
            }
        } while (choice != 0);
    }

    /**
     * Allows interactive adding of a new match.
     * Validates team names and scores, and handles user input errors.
     */
    private void addMatchInteractive() {
        try {
            String teamA = readNonEmptyLineWithPrompt("Enter team A name: ");
            teamA = ensureTeamExistsInteractive(teamA);

            String teamB = readNonEmptyLineWithPrompt("Enter team B name: ");
            teamB = ensureTeamExistsInteractive(teamB);

            int scoreA = readIntWithPrompt("Score of " + teamA + ": ");
            int scoreB = readIntWithPrompt("Score of " + teamB + ": ");

            Match m = new Match(teamA, teamB, scoreA, scoreB);
            matchDb.addMatch(m);
            view.displayMatch(m);

        } catch (InvalidTeamNameException e) {
            view.displayError(e.getMessage());
        } catch (NumberFormatException e) {
            view.displayError("Score must be an integer.");
        } catch (Exception e) {
            view.displayError("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Ensures that a team exists in the database.
     * If the team is new, asks whether to add it or select from existing ones.
     *
     * @param name candidate team name (non-empty)
     * @return existing or newly added valid team name
     * @throws InvalidTeamNameException if the selection fails or no teams exist
     */
    private String ensureTeamExistsInteractive(String name) throws InvalidTeamNameException {
        if (teamDb.contains(name)) {
            return name;
        }

        while (true) {
            System.out.println("Team \"" + name + "\" does not exist.");
            String ans = readNonEmptyLineWithPrompt("Add as new (y/n)? [y=add, n=choose existing]: ")
                    .trim().toLowerCase();

            if (ans.equals("y") || ans.equals("yes")) {
                teamDb.addTeam(name);
                view.displayInfo("Team added: " + name);
                return name;
            } else if (ans.equals("n") || ans.equals("no")) {
                List<String> teams = teamDb.getTeams();
                if (teams.isEmpty()) {
                    throw new InvalidTeamNameException("No teams available to choose from.");
                }

                System.out.println("Choose existing team by index or type the exact name:");
                for (int i = 0; i < teams.size(); ++i) {
                    System.out.println(i + " -> " + teams.get(i));
                }

                String choice = readNonEmptyLineWithPrompt("Your choice (index or name): ");
                try {
                    int idx = Integer.parseInt(choice);
                    if (idx >= 0 && idx < teams.size()) {
                        return teams.get(idx);
                    } else {
                        view.displayError("Index out of range.");
                    }
                } catch (NumberFormatException ex) {
                    String trimmed = choice.trim();
                    if (teamDb.contains(trimmed)) {
                        return trimmed;
                    } else {
                        view.displayError("No team with that name.");
                    }
                }
            } else {
                System.out.println("Please answer 'y' or 'n'.");
            }
        }
    }

    /** Removes a selected match by index. */
    private void removeMatch() {
        view.displayMatches(matchDb.getMatches());
        int idx = readIntWithPrompt("Enter match index to remove: ");
        if (matchDb.removeMatch(idx))
            view.displayInfo("Removed match at index " + idx + ".");
        else
            view.displayError("Invalid index.");
    }

    /** Removes a team from the database by name. */
    private void removeTeam() {
        List<String> teams = teamDb.getTeams();
        view.displayTeams(teams);
        String name = readNonEmptyLineWithPrompt("Enter team name to remove: ");
        if (teamDb.removeTeam(name))
            view.displayInfo("Removed team: " + name);
        else
            view.displayError("Team not found.");
    }

    /**
     * Reads a non-empty line from input, repeating until valid.
     *
     * @param prompt text displayed before user input
     * @return non-empty trimmed string
     */
    private String readNonEmptyLineWithPrompt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            if (line == null) line = "";
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) return trimmed;
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    /**
     * Reads an integer from input, repeating until valid.
     *
     * @param prompt text displayed before user input
     * @return parsed integer
     */
    private int readIntWithPrompt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            if (line == null) line = "";
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
}
