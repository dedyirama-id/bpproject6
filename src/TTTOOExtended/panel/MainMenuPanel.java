package TTTOOExtended.panel;

import TTTOOExtended.GameMain;
import TTTOOExtended.MainFrame;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    private MainFrame mainFrame;

    public MainMenuPanel(CardLayout cardLayout, JPanel mainPanel, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        JButton historyButton = new JButton("📅 History");
        JButton startButton = new JButton("🚀 Start!");
        JButton exitButton = new JButton("🚫 Exit");

        Dimension buttonSize = new Dimension(200, 60);
            historyButton.setMaximumSize(buttonSize);
            startButton.setMaximumSize(buttonSize);
            exitButton.setMaximumSize(buttonSize);

        historyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(historyButton);
        add(Box.createVerticalStrut(15));
        add(startButton);
        add(Box.createVerticalStrut(15));
        add(exitButton);
        add(Box.createVerticalGlue());

        historyButton.addActionListener(e -> {
            mainFrame.setContentPane(new HistoryPanel(mainFrame));
            mainFrame.revalidate();
        });
        startButton.addActionListener(e -> {
            mainFrame.setContentPane(new GameMain(mainFrame));
            mainFrame.revalidate();
        });
        exitButton.addActionListener(_ -> System.exit(0));
    }
}
