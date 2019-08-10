package academy.learnprogramming;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class mainMenu {
    private JButton startGameButton;
    private JButton addGamesButton;
    private JTextField gameCollectorTextField;
    private JTextField byJonathanBergqvistTextField;
    private JPanel menuPanel;

    public mainMenu() {
        // When pressing button for starting game, then display the message
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(null, "Hello world!");
            }
        });
    }

    public static void main(String[] args) {
        JFrame menuFrame = new JFrame("mainMenu"); // Title of the window
        menuFrame.setContentPane(new mainMenu().startGameButton); // Set visible content to the content chosen from mainMenu
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Determine default close operation
        menuFrame.pack(); // Create window with preferred size based on its content
        menuFrame.setVisible(true); // Set visibility of the window


    }
}

