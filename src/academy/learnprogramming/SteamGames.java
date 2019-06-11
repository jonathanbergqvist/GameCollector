package academy.learnprogramming;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import static academy.learnprogramming.Main.steamName;

public class SteamGames {

    public static File[] findSteamGameFiles(String steamRootFolder) {
        // Find the .acf files with app id from the folder specified above
        File steamRootFile = new File(steamRootFolder);
        File[] steamFolderContent = steamRootFile.listFiles(new FilenameFilter() {
            public boolean accept(File steamRoot, String fileName) {
                return fileName.endsWith(".acf");
            }
        });
        return steamFolderContent;
    }

    public static void collectSteamGames(File[] steamFolderContent, Map gamesAndIdsCollection) {

        // Check if the game service is in file, if it is then skip adding games.
        ArrayList<String> gamesInFile = new GameServiceFileModifier().checkService(steamName);
        //System.out.println("TF: " + gamesInFile);

            // Find the app id and game name in each file
            for (File file : steamFolderContent) {
                // Start to scan file and add needed line to array
                try {
                    Scanner scannerFile = new Scanner(file);
                    int counter = 0;
                    ArrayList<String> linesInFile = new ArrayList<>();
                    while (scannerFile.hasNextLine() && counter < 5) { // Only the first 5 lines are needed
                        String lineContent = scannerFile.nextLine();
                        linesInFile.add(lineContent);
                        counter++;
                    }
                    String appIdLine = linesInFile.get(2); // App id on line 3
                    String[] appIdLines = appIdLine.split("\t");
                    String appIdForGameWithQuotes = appIdLines[3]; // Is \t unwantedString \t wantedString
                    int appIdForGameLength = appIdForGameWithQuotes.length();
                    String appIdForGame = appIdForGameWithQuotes.substring(1, appIdForGameLength - 1); // Remove unwanted ""

                    if (!(appIdForGame.equals("228980"))) { // Skip "Steamworks Common Redistributables" if found
                        String gameLine = linesInFile.get(4); // Game name on line 5
                        String[] gameLines = gameLine.split("\t");
                        String nameForGameWithQuotes = gameLines[3]; // Is \t unwantedString \t wantedString
                        int nameForGameLength = nameForGameWithQuotes.length();
                        String nameForGame = nameForGameWithQuotes.substring(1, nameForGameLength - 1); // Remove unwanted ""

                        // Check if game in ArrayList
                        if (!(gamesInFile.contains(nameForGame))) {

                            try {
                                FileWriter appendGameAndId = new FileWriter(new Main().fileName, true);
                                String steamLine = nameForGame + "," + steamName + "," + appIdForGame + "\n";
                                appendGameAndId.write(steamLine);
                                appendGameAndId.close();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }

                    }
                    scannerFile.close();
                } catch(Exception e){
                    System.out.println(e);
                }
            }
    }


    public static void startSteamGame(String gameName, String gameId) {

        // Start the Steam game through URI.
        try {
            System.out.println("Starting " + gameName);
            URI uri = new URI("steam://rungameid/" + gameId);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

