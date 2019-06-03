package academy.learnprogramming;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//TODO Gör följande:
// * KLAR: Kolla selectAllowedMenuChoice och startMenuChoice, då scanner == false inte verkar ge något, kolla därmed hur fel givna siffror hanteras.
// * KLAR: Se till att respektive spelsamlare kollar att den root finns, för att veta om installationen av klienten finns.
// * KLAR: Låt användaren kunna ange sökväg i respektive tjänst enligt de instruktioner jag ger.
// * KLAR: Se till att mappkontrollen kollar specifikt för varje klient m.a.p. dess mappändelse.
// * KLAR:    Se till att mappkontrollen börjar om med nytt försök när man skriver fel (kan inte skriva över variabel).
// *
// * Dokumentera den nuvarande koden med kommentarer och rensa undan oanvänd kod.
// *
// * EJ. Samla alla spel i den gemensamma listan gamesAndIdsInit.
// * Spara den gemensamma listan med spel som en fil, t ex XML eller JSON
// * Kunna lägga till fler spel från en klient som redan har spel, t ex från en annan mapp.
// * Låt programmet kolla om ovan nämnda fil finns vid start av programmet
// och gå in i starta spel eller lägga in spel som standard beroende på om filen finns.
// * Se till att kontroller av input är mer generella till antalet möjligheter i respektive meny.

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
                System.out.println("Starting game...");
                break;
            case 2:
                System.out.println("Adding game(s)...");
                agSelectGameService();
                break;
            case 0:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Please enter a valid number.");
                int newChoice = selectAllowedMenuChoice();
                startMenuChoice(newChoice);
        }
    }

    //// Add games ////
    public static void agSelectGameService() {
        // Choose game service provider.
        System.out.println("1. " + steamName);
        System.out.println("2. " + originName);
        System.out.println("3. " + uplayName);
        System.out.println("0. Exit");
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
                Map<String, String[]> gamesAndIdsCollectionFromSteam = new SteamGames().collectSteamGames(steamFolderContent, gamesAndIdsInit);
                //REMOVE AFTER OWN START CASES:
                // Start the Steam game
                //new SteamGames().startSteamGame(gamesAndIdsCollectionFromSteam);
                break;
            case 2:
                System.out.println(originName);
                System.out.println("The fold path is to \"Origin Games\", which defaults to \"C:\\Program Files (x86)\\Origin Games\"");
                // Get the correct Origin folder path.
                String originFolder = agWriteGameServiceFolder(originName, initString);
                // Find installed Origin games in the folder provided.
                File[] originFolderContent = new OriginGames().findOriginGameFiles(originFolder);
                // Collect the installed Origin games.
                Map<String, String[]> gamesAndIdsCollectionFromOrigin = new OriginGames().collectOriginGames(originFolderContent, gamesAndIdsInit);
                //System.out.println(originName + " games added.");
                //REMOVE AFTER OWN START CASES:
                // Start the Origin game
                //new OriginGames().startOriginGame(gamesAndIdsCollectionFromOrigin);
                break;
            case 3:
                System.out.println(uplayName);
                System.out.println("The fold path is to \"Ubisoft Game Launcher\", which defaults to \"C:\\Program Files (x86)\\Ubisoft\\Ubisoft Game Launcher\"");
                // Get the correct Uplay folder path.
                String uplayFolder = agWriteGameServiceFolder(uplayName, initString);
                // Find installed Uplay games in the folder provided and collect them.
                Map<String, String[]> gamesAndIdsCollectionFromUplay = new UplayGames().findAndCollectUplayGames(uplayFolder, gamesAndIdsInit);
                System.out.println(uplayName + " games added.");
                //REMOVE AFTER OWN START CASES:
                // Start the Uplay game
                //new UplayGames().startUplaygame(gamesAndIdsCollectionFromUplay);
                break;
            case 0:
                System.out.println("Exiting");
                break;
            default:
                System.out.println("Invalid number, try again.");
                agSelectGameService();
        }



        /*System.out.println("Write the folder for the service:");
        Scanner getRootFolder = new Scanner(System.in);
        String rootFolder = getRootFolder.nextLine();
        return rootFolder;*/

    }

    public static String agWriteGameServiceFolder(String gameClient, String initString) {
        // Get the user folder input and check that it is correct for the service in question
        Scanner getRootFolder = new Scanner(System.in);
        String rootFolder = "";

        do {
            System.out.println("Please write the fold path to the folder specified:");
            String getFolderPath = getRootFolder.nextLine();
            // Change single \ to double \ in windows folder path.
            String getFolderPathBackslash = getFolderPath.replace("\\", "\\\\");
            // Change initString to the folder path
            rootFolder = initString.replaceAll(".+", getFolderPathBackslash);

            // Check that the written folder path is allowed and points to the corresponding game service provider.
        } while (agCheckGameServiceFolderValidity(rootFolder, gameClient) == false);
        return rootFolder;
    }

    public static boolean agCheckGameServiceFolderValidity(String folderPath, String gameClient) {
        // Create File variable of the written folder path.
        File rootFolderCheck = new File(folderPath);
        // Check that the folder path exists, if not ask for a new input.
        if (!(rootFolderCheck.exists())) {
            System.out.println("The folder \"" + rootFolderCheck.toString() + "\" can't be found, please try again.");
            return false;
        } else {
            System.out.println("The folder exists");

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




}

