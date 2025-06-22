package TTTOOExtended;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new GridLayout(3, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        JButton startButton = new JButton("Start!");
        JButton exitButton = new JButton("Exit");

        startButton.addActionListener(_ -> cardLayout.show(mainPanel, "Game"));
        exitButton.addActionListener(_ -> System.exit(0));

        add(startButton);
        add(exitButton);
    }
}
