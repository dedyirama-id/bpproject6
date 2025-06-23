package TTTOOExtended;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    private MainFrame mainFrame;

    public MainMenuPanel(CardLayout cardLayout, JPanel mainPanel, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridLayout(3, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        JButton historyButton = new JButton("History");
        JButton startButton = new JButton("Start!");
        JButton exitButton = new JButton("Exit");

        historyButton.addActionListener(e -> {
            mainFrame.setContentPane(new HistoryPanel(mainFrame));
            mainFrame.revalidate();
        });
        startButton.addActionListener(e -> {
            mainFrame.setContentPane(new GameMain());
            mainFrame.revalidate();
        });
        exitButton.addActionListener(_ -> System.exit(0));


        add(historyButton);
        add(startButton);
        add(exitButton);
    }
}
