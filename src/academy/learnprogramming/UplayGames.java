package academy.learnprogramming;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import static academy.learnprogramming.Main.uplayName;

class UplayGames {

    static void findAndCollectUplayGames(String uplayGamesFolder) {
        String uplayRootGameFolder = uplayGamesFolder + "\\games";
        File uplayGamesFolderWithGames = new File(uplayRootGameFolder);
        File[] uplayGamesFolderContent = uplayGamesFolderWithGames.listFiles();

        // Configurations file containing the name of the .exe file for each available game.
        String uplayConfigFileLoc = uplayGamesFolder + "\\cache\\configuration\\configurations";
        File uplayConfigFile = new File(uplayConfigFileLoc);

        // Check if the game service is in file, if it is then skip adding games.
        ArrayList<String> gamesInFile = new GameServiceFileModifier().checkService(uplayName);
        System.out.println("TF: " + gamesInFile);

        for (File file : uplayGamesFolderContent) {
            String gameName = file.getName();

            try {
                // Get binary file to US-ASCII
                InputStream binaryData = new FileInputStream(uplayConfigFile);
                String uplayConfigFileASCII = IOUtils.toString(binaryData, StandardCharsets.US_ASCII);
                Scanner scannerFile = new Scanner(uplayConfigFileASCII);
                String correctGame = "No";
                while (scannerFile.hasNextLine()) {
                    String lineContent = scannerFile.nextLine();
                    String gameLine = "name: \"" + gameName + "\"";
                    if (lineContent.contains(gameLine)) {
                        correctGame = "Yes";
                    }
                    if (lineContent.contains("relative: ") && correctGame.contains("Yes")) {
                        // Get line with .exe file name, then remove the search pattern "relative: "
                        String exeName = lineContent.replaceAll("(.*relative: )", "");
                        // The file path to the game
                        String gamePath = uplayRootGameFolder + "\\" + gameName;
                        Path gameRootFolderPath= FileSystems.getDefault().getPath(gamePath);
                        boolean gameRootFolder = Files.exists(gameRootFolderPath);
                        if (gameRootFolder == true) {
                            Files.walk(Paths.get(gamePath))
                                    .filter(Files::isRegularFile)
                                    .forEach((f)->{
                                    String correctFile = f.toString();
                                    if (correctFile.contains(exeName)) {

                                        // Check if game in ArrayList
                                        if (!(gamesInFile.contains(gameName))) {

                                            try {
                                                FileWriter appendGameAndId = new FileWriter(new Main().fileName, true);
                                                String uplayLine = gameName + "," + uplayName + "," + correctFile + "\n";
                                                appendGameAndId.write(uplayLine);
                                                appendGameAndId.close();
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                        }

                                    }
                                    });
                            break;
                        }
                    }
                }
                scannerFile.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    static void startUplaygame(String gameName, String gameId) {

        // Get the parent folder for the .exe file needed to start game.
        File file = new File(gameId);
        String path = file.getParent();

        // Start the Uplay game through .exe file.
        try {
            System.out.println("Starting " + gameName);
            Runtime.getRuntime().exec(gameId, null, new File(path));

        } catch (Exception e) {
            System.out.println(e);
        }



    }
}
