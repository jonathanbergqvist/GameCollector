package academy.learnprogramming;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    // HashMap to store games (keys) and ids (values) with
    public static Map<String, String[]> gamesAndIdsInit = new HashMap<>();
    // Define game services
    public static final String steamName = "Steam";
    public static final String originName = "Origin";
    public static final String uplayName = "Uplay";
    public static String fileName = "gameServicesAndGames.txt";

    public static void main(String[] args) {
        // Get to the availableMenuChoices function.
        availableMenuChoices();
        //new GameServiceFileModifier().createFile();
        //new GameServiceFileModifier().writeToFile();
        //boolean res = new GameServiceFileModifier().checkService(steamName);
        //System.out.println(res);
        //new GameServiceFileModifier().appendFile();
        //new GameServiceFileModifier().checkService(steamName);
    }

    public static void availableMenuChoices() {
        // Menu to list available choices.
        System.out.println("1. Start game");
        System.out.println("2. Add games");
        System.out.println("0. Exit");
        // Get user choice and control that the input is allowed.
        int choice = selectAllowedMenuChoice();
        // Start the corresponding action from the input integer.
        startMenuChoice(choice);
    }

    public static int selectAllowedMenuChoice() {
        // Get user input and make sure that the input is an allowed integer.
        Scanner scanner = new Scanner(System.in);
        int userChoice;
        do {
            System.out.println("Please enter the number for the option you choose:");
            while (!(scanner.hasNextInt())) {
                System.out.println("The input was not a correct integer.");
                scanner.next();
            }
            userChoice = scanner.nextInt();

        } while ((userChoice < 0) || (userChoice > 2)); // Menu list range in availableMenuChoices().

        return userChoice;

    }

    public static void startMenuChoice(int userChoice) {
        switch (userChoice) {
            case 1:
                sgStartGame();
                break;
            case 2:
                agSelectGameService();
                break;
            case 0:
                System.out.println("Exiting");
                System.exit(0);
            default:
                System.out.println("Please enter a valid number.");
                int newChoice = selectAllowedMenuChoice();
                startMenuChoice(newChoice);
        }
    }

    //// Add games ////
    public static void agSelectGameService() {
        // Choose game service provider.
        System.out.println("You can add games from these services:");
        System.out.println("1. " + steamName);
        System.out.println("2. " + originName);
        System.out.println("3. " + uplayName);
        System.out.println("0. Back");
        Scanner getServiceProvider = new Scanner(System.in);
        int serviceChoice;
        do {
            System.out.println("Write the service provider you want to add games from:");
            while(!(getServiceProvider.hasNextInt())) {
                System.out.println("The input was not a correct integer.");
                getServiceProvider.next();
            }
            serviceChoice = getServiceProvider.nextInt();
        } while ((serviceChoice < 0) || (serviceChoice > 3)); // Depends on availableMenuChoices in agSelectGameService().

        String initString = "init";
        switch(serviceChoice) {
            case 1:
                // Choose Steam
                System.out.println(steamName);
                System.out.println("The fold path is to \"steamapps\", which defaults to \"C:\\Program Files (x86)\\Steam\\steamapps\"");
                // Get the correct Steam folder path.
                String steamFolder = agWriteGameServiceFolder(steamName, initString);
                // Find installed Steam games in the folder provided.
                File[] steamFolderContent = new SteamGames().findSteamGameFiles(steamFolder);
                // Collect the installed Steam games.
                new SteamGames().collectSteamGames(steamFolderContent, gamesAndIdsInit);
                System.out.println("List of games updated\n");
                availableMenuChoices();
                break;
            case 2:
                System.out.println(originName);
                System.out.println("The fold path is to \"Origin Games\", which defaults to \"C:\\Program Files (x86)\\Origin Games\"");
                // Get the correct Origin folder path.
                String originFolder = agWriteGameServiceFolder(originName, initString);
                // Find installed Origin games in the folder provided.
                File[] originFolderContent = new OriginGames().findOriginGameFiles(originFolder);
                // Collect the installed Origin games.
                new OriginGames().collectOriginGames(originFolderContent, gamesAndIdsInit);
                System.out.println("List of games updated\n");
                availableMenuChoices();
                break;
            case 3:
                System.out.println(uplayName);
                System.out.println("The fold path is to \"Ubisoft Game Launcher\", which defaults to \"C:\\Program Files (x86)\\Ubisoft\\Ubisoft Game Launcher\"");
                // Get the correct Uplay folder path.
                String uplayFolder = agWriteGameServiceFolder(uplayName, initString);
                // Find installed Uplay games in the folder provided and collect them.
                new UplayGames().findAndCollectUplayGames(uplayFolder, gamesAndIdsInit);
                System.out.println("List of games updated\n");
                availableMenuChoices();
                break;
            case 0:
                System.out.println("Backing");
                availableMenuChoices();
                break;
            default:
                System.out.println("Invalid number, try again.");
                agSelectGameService();
        }





    }

    public static String agWriteGameServiceFolder(String gameClient, String initString) {
        // Get the user folder input and check that it is correct for the service in question
        Scanner getRootFolder = new Scanner(System.in);
        String rootFolder = "";

        do {
            System.out.println("Please write the fold path to the folder specified:");
            System.out.println("To back, press 0.");
            String getFolderPath = getRootFolder.nextLine();
            // Change single \ to double \ in windows folder path.
            String getFolderPathBackslash = getFolderPath.replace("\\", "\\\\");
            // Change initString to the folder path
            rootFolder = initString.replaceAll(".+", getFolderPathBackslash);

            // Check that the written folder path equals 0, or is allowed and points to the corresponding game service provider.
        } while (agCheckGameServiceFolderValidity(rootFolder, gameClient) == false);
        return rootFolder;
    }

    public static boolean agCheckGameServiceFolderValidity(String folderPath, String gameClient) {
        // Create File variable of the written folder path.
        File rootFolderCheck = new File(folderPath);
        // Check that the folder path exists, if not ask for a new input.
        if (folderPath.equals("0")) {
            System.out.println("Backing");
            agSelectGameService();
            return false;
        } else if (!(rootFolderCheck.exists())) {
            System.out.println("The folder \"" + rootFolderCheck.toString() + "\" can't be found, please try again.");
            return false;
        } else {
            // Get name of the folder and check if it is one of the game services, if not ask for a new user input.
            String folderEnd = rootFolderCheck.getName();

            if (gameClient.equals(steamName)) {
                System.out.println(folderEnd + " is the correct folder path");
                return true;
            } else if (gameClient.equals(originName)) {
                System.out.println(folderEnd + " is the correct folder path");
                return true;
            } else if (gameClient.equals(uplayName)) {
                System.out.println(folderEnd + " is the correct folder path");
                return true;
            } else {
                System.out.println("Incorrect service written, please try again.");
                return false;
            }
        }
    }
    ///////////////////

    //// Start game ////
    public static void sgStartGame() {
        StartGame.listGames();
        StartGame.getGameChoice();

    }




}

