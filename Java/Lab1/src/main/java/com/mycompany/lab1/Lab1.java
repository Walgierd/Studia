/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1;

import com.mycompany.lab1.controller.MatchController;
import com.mycompany.lab1.model.*;
import com.mycompany.lab1.view.MatchView;

/**
 * Main entry point for the Counter-Strike Match Manager application.
 * <p>
 * Command-line parameters order:
 * <pre>
 * args[0] = teamA (String)
 * args[1] = teamB (String)
 * args[2] = scoreA (int)
 * args[3] = scoreB (int)
 * </pre>
 * 
 * <p>
 * Example:
 * <pre>
 * java -jar Lab1.jar "NAVI" "G2" 16 13
 * </pre>
 * 
 * This will automatically add a match between "NAVI" and "G2" with scores 16:13.
 * </p>
 * 
 * <p>
 * If the user provides no or incorrect arguments, 
 * the application will switch to interactive console mode.
 * </p>
 *
 * @author trela
 * @version 1.1
 */
public class Lab1 {

    /**
     * Application entry point.
     * 
     * @param args command-line parameters (see class description for order)
     */
    public static void main(String[] args) {
        MatchDatabase matchDb = new MatchDatabase();
        TeamDatabase teamDb = new TeamDatabase();
        MatchView view = new MatchView();
        MatchController controller = new MatchController(matchDb, teamDb, view);

        if (args.length == 4) {
            try {
                String teamA = args[0].trim();
                String teamB = args[1].trim();
                int scoreA = Integer.parseInt(args[2].trim());
                int scoreB = Integer.parseInt(args[3].trim());

                teamDb.addTeam(teamA);
                teamDb.addTeam(teamB);

                Match m = new Match(teamA, teamB, scoreA, scoreB);
                matchDb.addMatch(m);

                view.displayInfo("Match added from command-line arguments:");
                view.displayMatch(m);

            } catch (NumberFormatException e) {
                view.displayError("Scores must be integers. Switching to interactive mode...");
                controller.start();
            } catch (InvalidTeamNameException e) {
                view.displayError("Invalid team name: " + e.getMessage());
                controller.start();
            } catch (Exception e) {
                view.displayError("Unexpected error: " + e.getMessage());
                controller.start();
            }
        } else {

            controller.start();
        }
    }
}
