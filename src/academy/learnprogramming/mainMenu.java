package academy.learnprogramming;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class mainMenu {
    private JButton startGameButton;
    private JButton addGamesButton;
    private JPanel menuPanel;
    private JTextArea gameCollectorTextArea;
    private JList jListGameList;

    public mainMenu() {

        // Get the games to the list
        jListGameList.setListData(StartGame.listGames());

        // When pressing button for starting game, then display the message
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hello world!");
                //Main.sgListGamesGUI();
            }
        });

        addGamesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hello world!");
            }
        });

        menuPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });

        jListGameList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    JOptionPane.showMessageDialog(null, "Hello world!");
                    String game = (String) jListGameList.getSelectedValue();
                    StartGame.getGameChoice(game);
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {
        //Main.main(null);
        JFrame menuFrame = new JFrame("Game Collector"); // Title of the window
        menuFrame.setContentPane(new mainMenu().menuPanel); // Set visible content to the content chosen from mainMenu
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Determine default close operation
        menuFrame.pack(); // Create window with preferred size based on its content
        menuFrame.setVisible(true); // Set visibility of the window

    }


}

