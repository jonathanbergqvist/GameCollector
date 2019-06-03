package academy.learnprogramming;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

import static academy.learnprogramming.Main.originName;

public class OriginGames {

    public static File[] findOriginGameFiles(String originRootPath) {

        // Same the game folders.
        File originRootFolder = new File(originRootPath);
        File[] originFolderContent = originRootFolder.listFiles();

        return originFolderContent;
    }

    public static Map<String, String[]> collectOriginGames(File[] originFolderContent, Map gamesAndIdsCollection) {
        boolean checkIfInFile = new GameServiceFileModifier().checkService(originName);
        System.out.println("TF: " + checkIfInFile);
        if (checkIfInFile == false) {
            try {
                FileWriter appendService = new FileWriter(new Main().fileName, true);
                String serviceString = ">0< " + originName + "\n";
                appendService.write(serviceString);
                appendService.close();
            } catch (Exception e) {
                System.out.println(e);

            }


        // Look at each game folder to find game id
        for (File gameFolder : originFolderContent) {
            String gameName = gameFolder.getName();

            String installerPath = gameFolder.getAbsolutePath() + "\\__Installer\\installerdata.xml";
            File installerGameFile = new File(installerPath);
            if (installerGameFile.exists()) {
                try {
                    // Get contentIds for the game
                    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                    Document document = documentBuilder.parse(installerGameFile);
                    NodeList contentIds = document.getElementsByTagName("contentID");

                    // Get each contentId for game as String
                    int amountContentIds = contentIds.getLength();
                    // As ArrayList<String>
                    ArrayList<String> gameIdCollectionArrayList = new ArrayList<String>();
                    for (int i = 0; i < amountContentIds; i++) {
                        Node nodeId = contentIds.item(i);
                        String stringId = nodeId.getTextContent();
                        gameIdCollectionArrayList.add(stringId);
                    }
                    // As String[]
                    String[] gameIdCollectionStringArray = gameIdCollectionArrayList.toArray(new String[0]);
                    // As single String
                    String gameIdCollectionAsString = String.join(", ", gameIdCollectionStringArray);

                    //String[] serviceAndId = {originName, gameIdCollectionAsString};
                    //gamesAndIdsCollection.put(gameName, serviceAndId);

                    try {
                        FileWriter appendGameAndId = new FileWriter(new Main().fileName, true);
                        String gameString = ">1< " + gameName+ "\n";
                        String idString = ">2< " + gameIdCollectionAsString + "\n";
                        appendGameAndId.write(gameString);
                        appendGameAndId.write(idString);
                        appendGameAndId.close();
                    } catch (Exception e) {
                        System.out.println(e);

                    }

                } catch (Exception e) {
                    System.out.println(e);
                }

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

    /*public static void startOriginGame(Map<String, String[]> gamesAndsIds) {
         // Start the game written in .get() below
        String[] gameArray = gamesAndsIds.get("Plants vs. Zombies");
        String service = gameArray[0];
        String game = gameArray[1];
        try {
            URI uri = new URI("origin://launchgame/" + game);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
            }
        } catch (Exception e) {
            System.out.println(e);
        }


    }*/

}
