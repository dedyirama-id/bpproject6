package TTTOOExtended.panel;

import TTTOOExtended.MainFrame;
import TTTOOExtended.model.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
        JButton startButton = new JButton("🚀 Play vs Player!");
        JButton aiButton = new JButton("🤖 Play vs AI");
        JButton exitButton = new JButton("🚫 Exit");

        JComboBox<String> aiLevelSelector = new JComboBox<>(new String[]{
                "easy", "medium", "hard"
        });
        Dimension buttonSize = new Dimension(200, 60);
        aiLevelSelector.setMaximumSize(buttonSize);
        aiLevelSelector.setAlignmentX(Component.CENTER_ALIGNMENT);


        buttonSize = new Dimension(200, 60);
        historyButton.setMaximumSize(buttonSize);
        customSoundButton.setMaximumSize(buttonSize);
        customIconButton.setMaximumSize(buttonSize);
        startButton.setMaximumSize(buttonSize);
        aiButton.setMaximumSize(buttonSize);
        exitButton.setMaximumSize(buttonSize);

        historyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customSoundButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        aiButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customIconButton.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        add(aiButton);
        add(Box.createVerticalStrut(15));
        add(exitButton);
        add(Box.createVerticalGlue());

        // Actions
        customIconButton.addActionListener(_ -> {
            mainFrame.getMainPanel().add(new CustomIconPanel(mainFrame), "CustomIcon");
            mainFrame.getCardLayout().show(mainFrame.getMainPanel(), "CustomIcon");
        });
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

        String[] selectedChar = new String[1];
        aiButton.addActionListener(_ -> {
            // Step 1: Pilih karakter
            selectedChar[0] = "X";

            String[] chars = {"X", "O"};
            selectedChar[0] = (String) JOptionPane.showInputDialog(
                    this,
                    "Pilih karakter kamu:",
                    "Pilih Karakter",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    chars,
                    chars[0]
            );

            if (selectedChar == null) return;

            // Step 2: Pilih difficulty
            String[] options = {"easy", "medium", "hard"};
            String selected = (String) JOptionPane.showInputDialog(
                    this,
                    "Pilih tingkat kesulitan AI:",
                    "Pilih Difficulty",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2]
            );

            if (selected != null) {
                mainFrame.startGame(true, Arrays.toString(selectedChar), selected);
            }

            if (selected != null) mainFrame.startGame(true, selected, selectedChar[0]);
        });

        startButton.addActionListener(_ -> {
            mainFrame.startGame(false, "normal", selectedChar[0]);
        });

        aiButton.addActionListener(_ -> {
            mainFrame.startGame(true, "hard", selectedChar[0]);
        });
    }

    class GameModePanel extends JPanel {
        public JButton btnPvP;
        public JButton btnPvAI;
        public JButton btnRestart;

        public GameModePanel(ActionListener listener) {
            setLayout(new FlowLayout(FlowLayout.CENTER));

            btnPvP = new JButton("Player vs Player");
            btnPvAI = new JButton("Player vs AI");
            btnRestart = new JButton("Restart");

            btnPvP.addActionListener(listener);
            btnPvAI.addActionListener(listener);
            btnRestart.addActionListener(listener);

            add(btnPvP);
            add(btnPvAI);
            add(btnRestart);
        }
    }
}
