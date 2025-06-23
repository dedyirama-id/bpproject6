package TTTOOExtended.panel;

import TTTOOExtended.service.DBService;
import TTTOOExtended.model.GameHistory;
import TTTOOExtended.MainFrame;
import TTTOOExtended.model.Session;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * HistoryPanel displays a list of past game sessions for the current user,
 * including navigation controls and game board rendering.
 */
public class HistoryPanel extends JPanel {
    private JLabel lblTimestamp;
    private JLabel lblWinner;
    private JButton[][] cellButtons;
    private JButton btnPrev, btnNext;
    private List<GameHistory> histories;
    private int currentIndex = 0;
    private MainFrame mainFrame;

    public HistoryPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Header
        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        lblTimestamp = new JLabel("");
        lblWinner = new JLabel("Winner: ");
        topPanel.add(lblTimestamp);
        topPanel.add(lblWinner);
        add(topPanel, BorderLayout.NORTH);

        // Board
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

        // Navigation
        JPanel navPanel = new JPanel();
        btnPrev = new JButton("Prev");
        btnNext = new JButton("Next");

        btnPrev.addActionListener(e -> {
            if (currentIndex < histories.size() - 1) {
                currentIndex++;
                loadHistory();
            }
        });

        btnNext.addActionListener(e -> {
            if (currentIndex > 0) {
                currentIndex--;
                loadHistory();
            }
        });

        JButton btnBack = new JButton("Back to Menu");
        btnBack.addActionListener(e -> {
            mainFrame.setContentPane(new MainMenuPanel(mainFrame.getCardLayout(), mainFrame.getMainPanel(), mainFrame));
            mainFrame.revalidate();
        });

        navPanel.add(btnBack);
        navPanel.add(btnPrev);
        navPanel.add(btnNext);
        add(navPanel, BorderLayout.SOUTH);

        loadUserHistories();
    }

    // Loads the user's game history from the database
    private void loadUserHistories() {
        histories = DBService.getGameHistoriesByUserId(Session.getCurrentUserId());
        if (!histories.isEmpty()) {
            currentIndex = 0;
            loadHistory();
        } else {
            lblTimestamp.setText("No history.");
            lblWinner.setText("");
            btnPrev.setEnabled(false);
            btnNext.setEnabled(false);
        }
    }

    // Displays the game state at the current index
    private void loadHistory() {
        GameHistory game = histories.get(currentIndex);
        lblTimestamp.setText(game.timestamp);
        lblWinner.setText("Winner: " + game.winner);

        String[][] board = game.parseBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cellButtons[i][j].setText(board[i][j]);
            }
        }

        btnPrev.setEnabled(currentIndex < histories.size() - 1);
        btnNext.setEnabled(currentIndex > 0);
    }
}
