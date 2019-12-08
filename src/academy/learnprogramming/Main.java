package academy.learnprogramming;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

class Main {

    // Define game services
    static final String steamName = "Steam";
    static final String originName = "Origin";
    static final String uplayName = "Uplay";
    static final String eglName = "Epic Games Launcher";
    static String fileName = "gameServicesAndGames.txt";

    public static void main(String[] args) throws IOException {
        // Check that the file to store games in exists, if not create the file
        File fileCheck = new File(fileName);
        final Path path = fileCheck.toPath();
        if (Files.notExists(path)) {
            fileCheck.createNewFile();
        }

        // Get to the availableMenuChoices function.
        availableMenuChoices();
    }

    static void availableMenuChoices() {
        // Menu to list available choices.
        System.out.println("1. Start game");
        System.out.println("2. Add games");
        System.out.println("0. Exit");
        // Get user choice and control that the input is allowed.
        int choice = selectAllowedMenuChoice();
        // Start the corresponding action from the input integer.
        startMenuChoice(choice);
    }

    private static int selectAllowedMenuChoice() {
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

    private static void startMenuChoice(int userChoice) {
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

    // List game services in GUI as Object
    static Object[] guiViewGameServices() {
        ArrayList<String> gamesServicesArrayList = new ArrayList<>();
        gamesServicesArrayList.add(steamName);
        gamesServicesArrayList.add(originName);
        gamesServicesArrayList.add(uplayName);
        gamesServicesArrayList.add(eglName);

        Object[] gamesServicesObject = gamesServicesArrayList.toArray();

        return gamesServicesObject;
    }


    static String guiSelectedGameService(String gameServiceSelected) {

        String gameServiceDefaultPath;
        switch(gameServiceSelected) {
            case steamName:
                // Choose Steam
                System.out.println(steamName);
                gameServiceDefaultPath = "\"C:\\Program Files (x86)\\Steam\\steamapps\"";
                System.out.println("The fold path is to \"steamapps\", which defaults to " + gameServiceDefaultPath);

                break;
            case originName:
                System.out.println(originName);
                gameServiceDefaultPath = "\"C:\\Program Files (x86)\\Origin Games\"";
                System.out.println("The fold path is to \"Origin Games\", which defaults to " + gameServiceDefaultPath);
                break;
            case uplayName:
                System.out.println(uplayName);
                gameServiceDefaultPath = "\"C:\\Program Files (x86)\\Ubisoft\\Ubisoft Game Launcher\"";
                System.out.println("The fold path is to \"Ubisoft Game Launcher\", which defaults to " + gameServiceDefaultPath);
                break;
            case eglName:
                System.out.println(eglName);
                gameServiceDefaultPath = "\"C:\\Program Files\\Epic Games\"";
                System.out.println("The fold path is to \"Epic Games Launcher\", which defaults to " + gameServiceDefaultPath);
                break;
            default:
                gameServiceDefaultPath = null;
                break;
        }

        return gameServiceDefaultPath;
    }

    static void guiFindGamesForService(String gameServiceSelected, String gameServiceFolder) {

        //File[] foundGames;
        switch(gameServiceSelected) {
            case steamName:
                File[] foundSteamGames = new SteamGames().findSteamGameFiles(gameServiceFolder);
                new SteamGames().collectSteamGames(foundSteamGames);
                break;
            case originName:
                File[] foundOriginGames = new OriginGames().findOriginGameFiles(gameServiceFolder);
                new OriginGames().collectOriginGames(foundOriginGames);
                break;
            case uplayName:
                new UplayGames().findAndCollectUplayGames(gameServiceFolder);
                break;
            case eglName:
                new EGLGames().findAndCollectEGLGames(gameServiceFolder);
                break;
            default:
                break;
        }
    }



    //// Add games ////
    private static void agSelectGameService() {
        // Choose game service provider.
        System.out.println("You can add games from these services:");
        System.out.println("1. " + steamName);
        System.out.println("2. " + originName);
        System.out.println("3. " + uplayName);
        System.out.println("4. " + eglName);
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
        } while ((serviceChoice < 0) || (serviceChoice > 4)); // Depends on availableMenuChoices in agSelectGameService().

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
                new SteamGames().collectSteamGames(steamFolderContent);
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
                new OriginGames().collectOriginGames(originFolderContent);
                System.out.println("List of games updated\n");
                availableMenuChoices();
                break;
            case 3:
                System.out.println(uplayName);
                System.out.println("The fold path is to \"Ubisoft Game Launcher\", which defaults to \"C:\\Program Files (x86)\\Ubisoft\\Ubisoft Game Launcher\"");
                // Get the correct Uplay folder path.
                String uplayFolder = agWriteGameServiceFolder(uplayName, initString);
                // Find installed Uplay games in the folder provided and collect them.
                new UplayGames().findAndCollectUplayGames(uplayFolder);
                System.out.println("List of games updated\n");
                availableMenuChoices();
                break;
            case 4:
                System.out.println(eglName);
                System.out.println("The fold path is to \"Epic Games Launcher\", which defaults to \"C:\\Program Files\\Epic Games\"");
                String eglFolder = agWriteGameServiceFolder(eglName, initString);
                new EGLGames().findAndCollectEGLGames(eglFolder);
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

    private static String agWriteGameServiceFolder(String gameClient, String initString) {
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

    private static boolean agCheckGameServiceFolderValidity(String folderPath, String gameClient) {
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
            } else if (gameClient.equals(eglName)) {
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
    static void sgStartGame() {
        StartGame.listGames();
        //tartGame.getGameChoice();

    }


    static void sgListGamesGUI() {
        StartGame.listGames();
    }




}

