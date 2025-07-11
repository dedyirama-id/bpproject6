package TTTOOExtended.panel;

import TTTOOExtended.model.GameHistory;
import TTTOOExtended.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * HistoryPanel displays a list of past game sessions,
 * including navigation controls and game board rendering.
 */
public class HistoryPanel extends JPanel {
    private final JLabel lblTimestamp;
    private final JLabel lblWinner;
    private final JButton[][] cellButtons;
    private final JButton btnPrev, btnNext;
    private final GameHistory[] histories;
    private int currentIndex = 0;

    public HistoryPanel(MainFrame mainFrame, GameHistory[] histories) {
        this.histories = histories;
        setLayout(new BorderLayout());

        // Header panel
        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        lblTimestamp = new JLabel("");
        lblWinner = new JLabel("Winner: ");
        topPanel.add(lblTimestamp);
        topPanel.add(lblWinner);
        add(topPanel, BorderLayout.NORTH);

        // Board panel
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        cellButtons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton cell = new JButton();
                cell.setEnabled(false);
                cell.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
                boardPanel.add(cell);
                cellButtons[i][j] = cell;
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // Navigation panel
        JPanel navPanel = new JPanel();
        btnPrev = new JButton("Prev");
        btnNext = new JButton("Next");

        btnPrev.addActionListener(_ -> {
            if (currentIndex < histories.length - 1) {
                currentIndex++;
                loadHistory();
            }
        });

        btnNext.addActionListener(_ -> {
            if (currentIndex > 0) {
                currentIndex--;
                loadHistory();
            }
        });

        JButton btnBack = new JButton("Back to Menu");
        btnBack.addActionListener(_ -> mainFrame.getCardLayout().show(mainFrame.getMainPanel(), "MainMenu"));

        navPanel.add(btnBack);
        navPanel.add(btnPrev);
        navPanel.add(btnNext);
        add(navPanel, BorderLayout.SOUTH);

        // Initial display
        if (histories.length > 0) {
            loadHistory();
        } else {
            lblTimestamp.setText("No history.");
            lblWinner.setText("");
            btnPrev.setEnabled(false);
            btnNext.setEnabled(false);
        }
    }

    private void loadHistory() {
        GameHistory game = histories[currentIndex];
        lblTimestamp.setText(game.timestamp);
        lblWinner.setText("Winner: " + game.winner);

        String[][] board = game.parseBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cellButtons[i][j].setText(board[i][j]);
            }
        }

        btnPrev.setEnabled(currentIndex < histories.length - 1);
        btnNext.setEnabled(currentIndex > 0);
    }
}
