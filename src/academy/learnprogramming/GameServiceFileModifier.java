package academy.learnprogramming;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

class GameServiceFileModifier {

    static ArrayList<String> checkService(String service) {
        //System.out.println("Reading file of saved games");
        ArrayList<String> gameNamesInFileForService = new ArrayList<>();
        try {
            // Read file
            File fileReader = new File(new Main().fileName);
            Scanner readLineInFile = new Scanner(fileReader);
            while (readLineInFile.hasNextLine()) {
                String lineContent = readLineInFile.nextLine(); // Line in file

                // Split string into game, service and id
                String[] lineContentSplit = lineContent.split(",");
                String game = lineContentSplit[0]; // Game

                // Add games in file for service to ArrayList
                if (lineContentSplit[1].equals(service)) {
                    gameNamesInFileForService.add(game);
                    //System.out.println(gameNamesInFileForService);
                }
            }
            readLineInFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        //System.out.println("Done" + gameNamesInFileForService);
        return gameNamesInFileForService;
    }

}
