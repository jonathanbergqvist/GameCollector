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

public class UplayGames {

    public static Map<String, String[]> findAndCollectUplayGames(String uplayGamesFolder, Map gamesAndIdsCollection) {
        String uplayRootGameFolder = uplayGamesFolder + "\\games";
        File uplayGamesFolderWithGames = new File(uplayRootGameFolder);
        File[] uplayGamesFolderContent = uplayGamesFolderWithGames.listFiles();

        // Configurations file containing the name of the .exe file for each available game.
        String uplayConfigFileLoc = uplayGamesFolder + "\\cache\\configuration\\configurations";
        File uplayConfigFile = new File(uplayConfigFileLoc);

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
                        Files.walk(Paths.get(gamePath))
                                .filter(Files::isRegularFile)
                                .forEach((f)->{
                                String correctFile = f.toString();
                                if (correctFile.contains(exeName)) {
                                    String[] serviceAndGame = {"Uplay", correctFile};
                                    gamesAndIdsCollection.put(gameName, serviceAndGame);
                                }
                                });
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
        int gameToStartLength = game.length();
        int getPathSubstringLength = gameToStartLength - gameKeyLength - 5; // 5 equals the / and .exe
        String folderWithGameToStart = game.substring(0, getPathSubstringLength);

        // Start the game.
        try {
            System.out.println("Starting game");
            Runtime.getRuntime().exec(game, null, new File(folderWithGameToStart));

        } catch (Exception e) {
            System.out.println(e);
        }



    }
}
