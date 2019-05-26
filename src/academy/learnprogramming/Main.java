package academy.learnprogramming;

import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    // HashMap to store games (keys) and ids (values) with
    public static Map<String, String[]> gamesAndIdsInit = new HashMap<>();

    public static void main(String[] args) {

        menu();
        //File[] steamGameFiles = findSteamGameFiles();
        //Map<String, String> gameList = collectSteamGames(steamGameFiles, gamesAndIds);
        //startGame(gameList);
    }

    public static void menu() {
        // Menu to list available choices.
        System.out.println("1. Start game");
        System.out.println("2. Add games");
        System.out.println("0. Exit");
        int choice = menuList(); // Get user choice and control that the input is allowed.
        userChooses(choice); // Start the corresponding action from the input integer.

    }

    public static void userChooses(int userChoice) {
        switch (userChoice) {
            case 1:
                System.out.println("Starting game...");
                break;
            case 2:
                System.out.println("Adding game(s)...");
                break;
            case 0:
                System.out.println("Exiting...");
                break;
            case -1:
                System.out.println("TESTCASE...");
                testXGamesMethod();
                break;
            default:
                int newChoice = menuList();
                userChooses(newChoice);
        }
    }

    public static int menuList() {

        System.out.println("Please enter the number for the option you choose:");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt() == false) {
            System.out.println("Please enter an integer from the list above.");
            scanner.next();
        }
        int userChoice = scanner.nextInt();
        System.out.println("You entered number " + userChoice + ("."));
        return userChoice;

    }

    public static void lookForGamesFile() {
        // Look for a file containing all installed games.
        // If file is not available, create it through scripts below.
    }

    public static void testXGamesMethod() {
        // Method for testing methods from xGames.java, where x is the game client supported.


        // Test of OriginGames Class usage. WORKS!
        /*File[] originFolder = new OriginGames().findOriginGameFiles();
        Map<String, String[]> gamesAndIdsCollection = new OriginGames().collectOriginGames(originFolder, Main.gamesAndIdsInit);
        new OriginGames().startOriginGame(gamesAndIdsCollection);*/

        // Test of UplayGames Class usage. WORKS!
        /*File[] uplayFolder = new UplayGames().findUplayGameFiles();
        Map<String, String[]> gamesAndIdsCollection = new UplayGames().collectUplayGames(uplayFolder, Main.gamesAndIdsInit);
        new UplayGames().startUplaygame(gamesAndIdsCollection);
        */

        // Test of SteamGamees Class usage. WORKS!
        /*File[] steamFolder = new SteamGames().findSteamGameFiles();
        Map<String, String[]> gamesAndIdsCollection = new SteamGames().collectSteamGames(steamFolder, Main.gamesAndIdsInit);
        new SteamGames().startSteamGame(gamesAndIdsCollection);
        */
    }

}
