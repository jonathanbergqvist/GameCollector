package academy.learnprogramming;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class mainMenu extends Component {
    private JButton startGameButton;
    private JButton addGamesButton;
    private JPanel menuPanel;
    private JTextArea gameCollectorTextArea;
    private JList jListGameList;
    private JPanel titlePanel;
    private JPanel availableGamesPanel;
    private JList jListGameServices;
    private JPanel availableGameServicesPanel;

    /* TODO:
    * Add games from service in GUI
    *   Have suggested file path as default for each service
    *   DONE. Let user enter wanted file path
    * Have button to stop/close adding games to return to game screen which is updated automatically.
    */

    public mainMenu() {

        // Initially hide the panel for adding game services.
        availableGameServicesPanel.setVisible(false);

        // Get the games to the list of games
        jListGameList.setListData(StartGame.listGames());

        // Pressing Add games button
        addGamesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jListGameList.setVisible(false); // Hide list of available games
                jListGameServices.setListData(Main.guiViewGameServices()); // Set the list with available game services
                availableGameServicesPanel.setVisible(true); // Set visibility for the panel containing the game services.

            }
        });

        // Select game service to add games from.
        jListGameServices.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    // Select game service
                    String gameService = (String) jListGameServices.getSelectedValue();
                    String gameServiceDefaultPath = Main.guiSelectedGameService(gameService);
                    File gameServiceDefaultFilePath = new File(gameServiceDefaultPath);

                    // Select the folder to check
                    final JFileChooser fc = new JFileChooser();
                    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fc.setCurrentDirectory(new File(System.getProperty("user.home"))); // Set home directory as default in file browser
                    /*if (gameServiceDefaultFilePath.exists()) {
                        System.out.println("YES");
                        fc.setCurrentDirectory(gameServiceDefaultFilePath);
                    } else {
                        System.out.println("NO");
                        fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                    }*/
                    int returnVal = fc.showOpenDialog(mainMenu.this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File gameServiceFolder = fc.getSelectedFile();
                        String gameServiceFolderString = gameServiceFolder.getAbsolutePath();
                        Main.guiFindGamesForService(gameService, gameServiceFolderString);
                        JOptionPane.showMessageDialog(null, "Any available games added");
                    }



                }
            }
        });

        // Double click a game title to start the game.
        jListGameList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    //JOptionPane.showMessageDialog(null, "Start game");
                    // Start game
                    String game = (String) jListGameList.getSelectedValue();
                    StartGame.getGameChoice(game);
                }
            }
        });


        ////////////////////////////////////////////////////////////////////////////

        /*menuPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });*/

        // When pressing button for starting game, then display the message
        /*startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hello world!");
                //Main.sgListGamesGUI();
            }
        });*/



    }

    public static void main(String[] args) throws IOException {
        //Main.main(null);
        JFrame menuFrame = new JFrame("Game Collector"); // Title of the window
        menuFrame.setContentPane(new mainMenu().menuPanel); // Set visible content to the content chosen from mainMenu
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Determine default close operation
        menuFrame.pack(); // Create window with preferred size based on its content
        menuFrame.setVisible(true); // Set visibility of the window
        menuFrame.setSize(new Dimension(300, 400)); // Set initial window size after pack() and setVisible()
        // Center window
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        menuFrame.setLocation((dim.width/2)-menuFrame.getWidth()/2, (dim.height/2)-menuFrame.getHeight()/2);


    }


}

