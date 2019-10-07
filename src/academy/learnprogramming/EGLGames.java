package academy.learnprogramming;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

import static academy.learnprogramming.Main.eglName;

class EGLGames {

    // From https://stackoverflow.com/questions/2559759/how-do-i-convert-camelcase-into-human-readable-names-in-java
    // To split a string with camel case.
    private static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    static void findAndCollectEGLGames(String eglGamesFolder) {
        File eglGamesFolderWithGames = new File(eglGamesFolder);
        File[] eglGamesFolderContent = eglGamesFolderWithGames.listFiles();

        // Check if the game service is in file, if it is then skip adding games.
        ArrayList<String> gamesInFile = new GameServiceFileModifier().checkService(eglName);

        // If folder contains content then look at the content (i.e. installed games).
        if (eglGamesFolderContent != null) {
            for (File gameFolder : eglGamesFolderContent) {

                // Game name
                String gameFolderGameName = gameFolder.getName();

                // Split game name to contain whitespaces
                String gameFolderGameNameSplit = splitCamelCase(gameFolderGameName);

                // Find only .exe files for a game folder and add to a list
                FilenameFilter filenameFilter = new FilenameFilter() {
                    @Override
                    public boolean accept(File gameFolder, String name) {
                        return name.contains(".exe");
                    }
                };
                File[] gameFolderContent = gameFolder.listFiles(filenameFilter);
                assert gameFolderContent != null;
                String gameFile = gameFolderContent[0].getPath();

                // Check if game in ArrayList, if not then add to file of games collected.
                if (!(gamesInFile.contains(gameFolderGameNameSplit))) {

                    try {
                        FileWriter appendGameAndId = new FileWriter(new Main().fileName, true);
                        String eglLine = gameFolderGameNameSplit + "," + eglName + "," + gameFile + "\n";
                        appendGameAndId.write(eglLine);
                        appendGameAndId.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }

    }

    static void startEGLgame(String gameName, String gameId) {

        // Get the parent folder for the .exe file needed to start game.
        File file = new File(gameId);
        String path = file.getParent();

        // Start the EGL game through .exe file.
        try {
            System.out.println("Starting " + gameName);
            Runtime.getRuntime().exec(gameId, null, new File(path));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
