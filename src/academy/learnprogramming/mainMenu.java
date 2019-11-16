package academy.learnprogramming;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Set;

public class mainMenu {
    private JButton startGameButton;
    private JButton addGamesButton;
    private JPanel menuPanel;
    private JTextArea gameCollectorTextArea;

    public mainMenu() {
        // When pressing button for starting game, then display the message
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hello world!");
                Main.sgStartGame();
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

