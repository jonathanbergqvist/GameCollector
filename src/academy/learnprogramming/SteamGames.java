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

    public static Map<String, String[]> collectSteamGames(File[] steamFolderContent, Map gamesAndIdsCollection) {
        boolean checkIfInFile = new GameServiceFileModifier().checkService(steamName);
        System.out.println("TF: " + checkIfInFile);
        if (checkIfInFile == false) {
            try {
                FileWriter appendService = new FileWriter(new Main().fileName, true);
                String serviceString = ">0< " + steamName + "\n";
                appendService.write(serviceString);
                appendService.close();
            } catch (Exception e) {
                System.out.println(e);

            }


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

                        // Add game name as key and game id as value to hashmap
                        //String[] serviceAndId = {new Main().steamName, appIdForGame};
                        //gamesAndIdsCollection.put(nameForGame, serviceAndId);

                        try {
                            FileWriter appendGameAndId = new FileWriter(new Main().fileName, true);
                            String gameString = ">1< " + nameForGame + "\n";
                            String idString = ">2< " + appIdForGame + "\n";
                            appendGameAndId.write(gameString);
                            appendGameAndId.write(idString);
                            appendGameAndId.close();
                        } catch (Exception e) {
                            System.out.println(e);

                        }


                        /*try {
                            FileWriter appendFile = new FileWriter(new Main().fileName, true);
                            String serviceString = ">0< " + steamName + "\n";
                            appendFile.write(serviceString);
                            appendFile.close();
                        } catch (Exception e) {
                            System.out.println(e);

                        }*/

                        /*boolean checkIfInFile = new GameServiceFileModifier().checkService(steamName);
                        if (!(checkIfInFile)) {

                        }*/
                    }
                    scannerFile.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            try {
                FileWriter appendServiceEnd = new FileWriter(new Main().fileName, true);
                String serviceString = ">3<\n";
                appendServiceEnd.write(serviceString);
                appendServiceEnd.close();
            } catch (Exception e) {
                System.out.println(e);

            }

        }
        return gamesAndIdsCollection;
    }


    /*public static void startSteamGame(Map<String, String[]> gamesAndIds) {
        //System.out.println("Games: " + gamesAndIds);

        // Start the game written in .get() below
        String[] gameArray = gamesAndIds.get("Fallout: New Vegas");
        String service = gameArray[0];
        String game = gameArray[1];
        try {
            URI uri = new URI("steam://rungameid/" + game);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }*/
}

