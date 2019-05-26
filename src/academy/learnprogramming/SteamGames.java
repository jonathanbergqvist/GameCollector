package academy.learnprogramming;

import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class SteamGames {
    public static File[] findSteamGameFiles() {
        // Create file root to list game folders from Steam
        String steamRootFolder = "C:\\Program Files (x86)\\Steam\\steamapps";

        // Find the .acf files with app id from the folder specified above
        File steamRootFile = new File(steamRootFolder);
        File[] steamFolderContent = steamRootFile.listFiles(new FilenameFilter() {
            public boolean accept(File steamRoot, String fileName) {
                return fileName.endsWith(".acf");
            }
        });
        return steamFolderContent;
    }

    public static Map<String, String[]> collectSteamGames(File[] steamFolderContent, Map gamesAndIdsCollection) {
        // Find the app id and game name in each file
        for (File file : steamFolderContent) {
            //System.out.println("File: " + file);
            //System.out.println("File: " + file.toString());

            // Start to scan file and add needed line to array
            try {
                Scanner scannerFile = new Scanner(file);
                int counter = 0;
                ArrayList<String> linesInFile = new ArrayList<>();
                while (scannerFile.hasNextLine() && counter < 5) { // Only the first 5 lines are needed
                    String lineContent = scannerFile.nextLine();
                    linesInFile.add(lineContent);
                    //System.out.println("Line: " + lineContent);
                    counter++;
                }
                String appIdLine = linesInFile.get(2); // App id on line 3
                String[] appIdLines = appIdLine.split("\t");
                String appIdForGameWithQuotes = appIdLines[3]; // Is \t unwantedString \t \t wantedString
                int appIdForGameLength = appIdForGameWithQuotes.length();
                String appIdForGame = appIdForGameWithQuotes.substring(1, appIdForGameLength - 1); // Remove unwanted ""

                if (!(appIdForGame.equals("228980"))) { // Skip "Steamworks Common Redistributables" if found
                    //System.out.println("Id: " + appIdForGame);
                    String gameLine = linesInFile.get(4); // Game name on line 5
                    String[] gameLines = gameLine.split("\t");
                    String nameForGameWithQuotes = gameLines[3]; // Is \t unwantedString \t \t wantedString
                    int nameForGameLength = nameForGameWithQuotes.length();
                    String nameForGame = nameForGameWithQuotes.substring(1, nameForGameLength - 1); // Remove unwanted ""
                    //System.out.println("Game: " + nameForGame);

                    // Add game name as key and game id as value to hashmap
                    String[] serviceAndId = {"Steam", appIdForGame};
                    gamesAndIdsCollection.put(nameForGame, serviceAndId);
                    //System.out.println(gamesAndIdsCollection);
                }
                scannerFile.close();
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        return gamesAndIdsCollection;
    }

    public static void startSteamGame(Map<String, String[]> gamesAndIds) {
        System.out.println("Games: " + gamesAndIds);

        // Start the game written in .get() below
        String[] gameArray = gamesAndIds.get("Fallout: New Vegas");
        String service = gameArray[0];
        String game = gameArray[1];
        System.out.println(service + " " + game);
        try {
            URI uri = new URI("steam://rungameid/" + game);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

