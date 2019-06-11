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

    public static void collectOriginGames(File[] originFolderContent, Map gamesAndIdsCollection) {
        // Check if the game service is in file, if it is then skip adding games.
        ArrayList<String> gamesInFile = new GameServiceFileModifier().checkService(originName);
        //System.out.println("TF: " + gamesInFile);

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

                    // Check if game in ArrayList
                    if (!(gamesInFile.contains(gameName))) {

                        try {
                            FileWriter appendGameAndId = new FileWriter(new Main().fileName, true);
                            String originLine = gameName + "," + originName + "," + gameIdCollectionAsString + "\n";
                            appendGameAndId.write(originLine);
                            appendGameAndId.close();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }
    }

    public static void startOriginGame(String gameName, String gameId) {
         // Start the Origin game through URI.
        try {
            System.out.println("Starting " + gameName);
            URI uri = new URI("origin://launchgame/" + gameId);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
            }
        } catch (Exception e) {
            System.out.println(e);
        }


    }

}
