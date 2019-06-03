package academy.learnprogramming;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class GameServiceFileModifier {
/*    //public static String fileName = "gameServicesAndGames.txt";
    public static void createFile() {
        File gamesAndServicesFile = new File(new Main().fileName);
        try {
            if (gamesAndServicesFile.createNewFile()) {
                System.out.println("File created: " + gamesAndServicesFile.getName() + " in " + gamesAndServicesFile.getAbsolutePath() + ".");
            } else {
                System.out.println("File " + gamesAndServicesFile.getName() + " already exists in " + gamesAndServicesFile.getAbsolutePath() + ".");
            }
            } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void writeToFile() {
        System.out.println("Writing to file.");
        try {
            FileWriter fileWriter = new FileWriter(new Main().fileName);
            fileWriter.write(">0< Testrow 0\n");
            fileWriter.write(">1< Testrow 1\n");
            fileWriter.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void appendFile() {
        System.out.println("Appending file");
        try {
            FileWriter fileAppender = new FileWriter(new Main().fileName, true);
            fileAppender.write(">2< Row 2");
            fileAppender.close();
        } catch (Exception e) {
            System.out.println(e);
        }




    }
*/
    public static boolean checkService(String service) {
        System.out.println("Reading file");
        try {
            String serviceString = ">0< " + service;
            File fileReader = new File(new Main().fileName);
            Scanner readLineInFile = new Scanner(fileReader);
            while (readLineInFile.hasNextLine()) {
                String lineContent = readLineInFile.nextLine();
                //System.out.println(lineContent);
                // <gameName, [gameService, gameFilePath]>
                // service, game, gamePath
                //String index = lineContent.substring(0,3);
                if (lineContent.equals(serviceString)) {
                    return true;
                }

                /*switch (index) {
                    case ">0<":
                        System.out.println("0");
                        if (lineContent.substring(4).equals(service)) {
                            return true;
                        }
                        break;
                }*/


                /*if (lineContent.substring(0,3).contains("Tes")) {
                    System.out.println("WORKS!");
                }*/

            }
            readLineInFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

}
