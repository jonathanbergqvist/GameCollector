package academy.learnprogramming;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

public class OriginGames {
    public static File[] findOriginGameFiles() {

        // Folder containing folders with each installed game
        String originRootPath = "C:\\Program Files (x86)\\Origin Games";

        // Same the game folders.
        File originRootFolder = new File(originRootPath);
        File[] originFolderContent = originRootFolder.listFiles();

        return originFolderContent;
    }

    public static Map<String, String[]> collectOriginGames(File[] originFolderContent, Map gamesAndIdsCollection) {

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
                    //System.out.println(contentIds.toString());

                    // Get each contentId for game as String
                    int amountContentIds = contentIds.getLength();
                    // As ArrayList<String>
                    ArrayList<String> gameIdCollectionArrayList = new ArrayList<String>();
                    for (int i = 0; i < amountContentIds; i++) {
                        Node nodeId = contentIds.item(i);
                        String stringId = nodeId.getTextContent();
                        gameIdCollectionArrayList.add(stringId);
                        //System.out.println("Id: " + stringId);

                    }
                    //System.out.println(gameIdCollectionArrayList);
                    // As String[]
                    String[] gameIdCollectionStringArray = gameIdCollectionArrayList.toArray(new String[0]);
                    // As single String
                    String gameIdCollectionAsString = String.join(", ", gameIdCollectionStringArray);
                    //System.out.println(gameIdCollectionAsString);

                    String[] serviceAndId = {"Origin", gameIdCollectionAsString};
                    gamesAndIdsCollection.put(gameName, serviceAndId);



                } catch (Exception e) {
                    System.out.println(e);
                }

            }

        }
        return gamesAndIdsCollection;
    }

    public static void startOriginGame(Map<String, String[]> gamesAndsIds) {
        //System.out.println("Games: " + gamesAndsIds);

        // Start the game written in .get() below
        String[] gameArray = gamesAndsIds.get("Plants vs. Zombies");
        String service = gameArray[0];
        String game = gameArray[1];
        //System.out.println(service + " " + game);
        try {
            URI uri = new URI("origin://launchgame/" + game);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
            }
        } catch (Exception e) {
            System.out.println(e);
        }


    }

}
