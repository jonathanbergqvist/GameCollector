package academy.learnprogramming;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class UplayGames {

    public static String uplayRootGameFolder = "C:\\Program Files (x86)\\Ubisoft\\Ubisoft Game Launcher\\games";

    public static File[] findUplayGameFiles() {

        // Folder containing each installed game as a folder each with the game name.


        File uplayGamesFolder = new File(uplayRootGameFolder);
        File[] uplayGamesFolderContent = uplayGamesFolder.listFiles();
        //System.out.println(uplayGamesFolderContent);
        return uplayGamesFolderContent;

    }

    public static Map<String, String[]> collectUplayGames(File[] uplayGamesFolderContent, Map gamesAndIdsCollection) {
        // Configurations file containing the name of the .exe file for each available game.
        String uplayConfigFileLoc = "C:\\Program Files (x86)\\Ubisoft\\Ubisoft Game Launcher\\cache\\configuration\\configurations";
        File uplayConfigFile = new File(uplayConfigFileLoc);
        //System.out.println(uplayConfigFile.canRead());

        for (File file : uplayGamesFolderContent) {
            String gameName = file.getName();
            //System.out.println("Game: " + gameName);

            try {
                // Get binary file to US-ASCII
                InputStream binaryData = new FileInputStream(uplayConfigFile);
                String uplayConfigFileASCII = IOUtils.toString(binaryData, StandardCharsets.US_ASCII);
                Scanner scannerFile = new Scanner(uplayConfigFileASCII);
                //System.out.println("File: " + uplayConfigFileASCII);
                String correctGame = "No";
                //System.out.println(correctGame);
                //System.out.println(scannerFile.hasNextLine());
                while (scannerFile.hasNextLine()) {
                    //System.out.println("Loop");
                    String lineContent = scannerFile.nextLine();
                    //System.out.println(lineContent);
                    String gameLine = "name: \"" + gameName + "\"";
                    if (lineContent.contains(gameLine)) {
                        correctGame = "Yes";
                        //System.out.println("nameLine: " + lineContent);

                    }
                    if (lineContent.contains("relative: ") && correctGame.contains("Yes")) {
                        // Get line with .exe file name, then remove the search pattern "relative: "
                        String exeName = lineContent.replaceAll("(.*relative: )", "");
                        //System.out.println("exeName: " + lineContent);
                        //String sub = lineContent.replaceAll("(.*relative: )", "");

                        //System.out.println("Sub: " + sub);

                        // The file path to the game
                        String gamePath = uplayRootGameFolder + "\\" + gameName;
                        //System.out.println(gamePath);

                        //File test = new File(gamePath);
                        Path gameRootFolderPath= FileSystems.getDefault().getPath(gamePath);
                        boolean gameRootFolder = Files.exists(gameRootFolderPath);
                        //System.out.println("Bool: " + gameRootFolder);
                        Files.walk(Paths.get(gamePath))
                                .filter(Files::isRegularFile)
                                .forEach((f)->{
                                String correctFile = f.toString();
                                if (correctFile.contains(exeName)) {
                                    //System.out.println("Correct file: " + correctFile);
                                    String[] serviceAndGame = {"Uplay", correctFile};
                                    gamesAndIdsCollection.put(gameName, serviceAndGame);
                                }
                                });
                        //System.out.println("Done");
                        //gameRootFolderPath)
                        //Path exeLocation = Files.find(gameRootFolderPath, Integer.MAX_VALUE, "exeName");
                        /*if (gameRootFolder.exists()) {
                            gameRootFolder.find
                        }      */

                        //gamesAndIdsCollection.put(gameName, exeName);
                        break;
                    }
                }
                scannerFile.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return gamesAndIdsCollection;
    }

    public static void startUplaygame (Map<String, String[]> gamesAndIds) {
        System.out.println("Games: " + gamesAndIds);

        String gameKey = "Rayman Origins"; // SHALL BE USER INPUT

        int gameKeyLength = gameKey.length();
        String[] gameArray = gamesAndIds.get(gameKey);
        String service = gameArray[0];
        String game = gameArray[1];
        System.out.println(service + " " + game);
        int gameToStartLength = game.length();
        int getPathSubstringLength = gameToStartLength - gameKeyLength - 5; // 5 equals the / and .exe
        String folderWithGameToStart = game.substring(0, getPathSubstringLength);
        //System.out.println("Sub: " + folderWithGameToStart);

        // Start the game.
        try {
            System.out.println("Starting game");
            Runtime.getRuntime().exec(game, null, new File(folderWithGameToStart));

        } catch (Exception e) {
            System.out.println(e);
        }



    }

}
