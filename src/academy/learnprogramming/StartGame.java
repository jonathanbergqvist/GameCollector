package academy.learnprogramming;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static academy.learnprogramming.Main.*;

class StartGame {

    static void listGames() {
        ArrayList<String> gameList = new ArrayList<>();
        try {
            // Read file
            File fileReader = new File(new Main().fileName);
            Scanner readLineInFile = new Scanner(fileReader);
            while (readLineInFile.hasNextLine()) {
                String lineContent = readLineInFile.nextLine(); // Line in file

                // Split string into game, service and id
                String[] lineContentSplit = lineContent.split(",");
                String game = lineContentSplit[0]; // Game
                //System.out.println(game);

                // Add each game to ArrayList
                gameList.add(game);

            }
            readLineInFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        // Sort ArrayList
        Collections.sort(gameList);

        // Print games
        System.out.println("Games available:");
        for (String game : gameList) {
            System.out.println(game);
        }

    }


    static void getGameChoice() {
        System.out.println("\nPlease write the name of the game you want to start.");
        System.out.println("Write 0 if you want to back.");
        System.out.println("The available games are listed above.");

        Scanner getGameChoice = new Scanner(System.in);
        while(!(getGameChoice.hasNextLine())) {
            System.out.println("The input was not a correct text input. Please try again.");
            getGameChoice.next();
        }
        String gameChoice = getGameChoice.nextLine();

        if (gameChoice.equals("0")) {
            System.out.println("Backing");
            Main.availableMenuChoices();
        }

        try {
            // Read file
            File fileReader = new File(new Main().fileName);
            Scanner readLineInFile = new Scanner(fileReader);

            boolean gameFileStatus = false;
            while (readLineInFile.hasNextLine()) {
                String lineContent = readLineInFile.nextLine(); // Line in file

                // Split line and get its content
                String[] lineContentSplit = lineContent.split(",");
                String gameName = lineContentSplit[0];
                String service = lineContentSplit[1];
                String gameId = lineContentSplit[2];

                // Get game name in lower case
                String lineContentLowerCase = gameName.toLowerCase();

                // If game name is correctly spelled, start the game with the correct service
                if (lineContentLowerCase.equals(gameChoice.toLowerCase())) {
                    gameFileStatus = true;

                    switch(service) {
                        case steamName:
                            new SteamGames().startSteamGame(gameName, gameId);
                            break;
                        case originName:
                            new OriginGames().startOriginGame(gameName, gameId);
                            break;
                        case uplayName:
                            new UplayGames().startUplaygame(gameName, gameId);
                            break;
                        case eglName:
                            new EGLGames().startEGLgame(gameName, gameId);
                            break;
                        default:
                            System.out.println("Something went wrong.");
                            break;
                    }
                    break;


                } else {
                    gameFileStatus = false;
                }

            }

            readLineInFile.close();

            // Make sure the input is allowed.
            if (gameFileStatus == false) {
                System.out.println("The input doesn't match a game name from the list. Please try again.");
                getGameChoice();
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
