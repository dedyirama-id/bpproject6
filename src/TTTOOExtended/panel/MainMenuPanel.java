package TTTOOExtended.panel;

import TTTOOExtended.MainFrame;
import TTTOOExtended.model.Session;

import javax.swing.*;
import java.awt.*;

/**
 * MainMenuPanel displays the main menu options: History, Start, and Exit.
 */
public class MainMenuPanel extends JPanel {

    public MainMenuPanel(final MainFrame mainFrame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        // Buttons
        JButton historyButton = new JButton("📅 History");
        JButton customSoundButton = new JButton("🎵 Custom Sound");
        JButton customIconButton = new JButton("🎨 Custom Icon");
        JButton startButton = new JButton("🚀 Start!");
        JButton exitButton = new JButton("🚫 Exit");

        Dimension buttonSize = new Dimension(200, 60);
        historyButton.setMaximumSize(buttonSize);
        customSoundButton.setMaximumSize(buttonSize);
        startButton.setMaximumSize(buttonSize);
        exitButton.setMaximumSize(buttonSize);

        historyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customSoundButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Layout
        add(Box.createVerticalGlue());
        add(historyButton);
        add(Box.createVerticalStrut(15));
        add(customSoundButton);
        add(Box.createVerticalStrut(15));
        add(customIconButton);
        add(Box.createVerticalStrut(15));
        add(startButton);
        add(Box.createVerticalStrut(15));
        add(exitButton);
        add(Box.createVerticalGlue());

        customIconButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customIconButton.addActionListener(_ -> {
            mainFrame.getMainPanel().add(new CustomIconPanel(mainFrame), "CustomIcon");
            mainFrame.getCardLayout().show(mainFrame.getMainPanel(), "CustomIcon");
        });


        // Actions
        historyButton.addActionListener(_ -> {
            var user = Session.getUser();
            if (user == null) {
                JOptionPane.showMessageDialog(this, "You must be logged in to view history.");
                return;
            }

            mainFrame.showHistoryPanelForUser(user.getId());
        });

        customSoundButton.addActionListener(_ -> {
            mainFrame.getMainPanel().add(new CustomSoundEffectPanel(mainFrame), "CustomSound");
            mainFrame.getCardLayout().show(mainFrame.getMainPanel(), "CustomSound");
        });

        startButton.addActionListener(_ -> mainFrame.getCardLayout().show(mainFrame.getMainPanel(), "Game"));

        exitButton.addActionListener(_ -> System.exit(0));
    }
}
