package TTTOOExtended.panel;

import TTTOOExtended.MainFrame;
import TTTOOExtended.model.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * MainMenuPanel displays the main menu options: History, Start, and Exit.
 */
public class MainMenuPanel extends JPanel {

    public MainMenuPanel(final MainFrame mainFrame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        // Buttons
        JButton historyButton = new JButton("📅 History");
        JButton startButton = new JButton("🚀 Play vs Player!");
        JButton aiButton = new JButton("🤖 Play vs AI");
        JButton exitButton = new JButton("🚫 Exit");

        JComboBox<String> aiLevelSelector = new JComboBox<>(new String[] {
                "easy", "medium", "hard"
        });
        Dimension buttonSize = new Dimension(200, 60);
        aiLevelSelector.setMaximumSize(buttonSize);
        aiLevelSelector.setAlignmentX(Component.CENTER_ALIGNMENT);


        buttonSize = new Dimension(200, 60);
        historyButton.setMaximumSize(buttonSize);
        startButton.setMaximumSize(buttonSize);
        exitButton.setMaximumSize(buttonSize);

        historyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Layout
        add(Box.createVerticalGlue());
        add(historyButton);
        add(Box.createVerticalStrut(15));
        add(startButton);
        add(Box.createVerticalStrut(15));
        add(exitButton);
        add(Box.createVerticalStrut(15));
        add(aiButton);
        add(Box.createVerticalGlue());


        // Actions
        historyButton.addActionListener(_ -> {
            var user = Session.getUser();
            if (user == null) {
                JOptionPane.showMessageDialog(this, "You must be logged in to view history.");
                return;
            }

            mainFrame.showHistoryPanelForUser(user.getId());
        });

        startButton.addActionListener(_ -> mainFrame.startGame(false, "hard")); // mode player
        aiButton.addActionListener(_ -> {
            String[] options = {"easy", "medium", "hard"};
            String selected = (String) JOptionPane.showInputDialog(
                    this, "Select AI Difficulty:", "Choose Difficulty",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
            if (selected != null) {
                mainFrame.startGame(true, selected); // mode AI
            }
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

