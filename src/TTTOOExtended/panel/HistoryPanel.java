package TTTOOExtended.panel;

import TTTOOExtended.service.DBService;
import TTTOOExtended.model.GameHistory;
import TTTOOExtended.MainFrame;
import TTTOOExtended.model.Session;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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

        // Header panel
        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding horizontal

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
                cellButtons[i][j] = new JButton();
                cellButtons[i][j].setEnabled(false);
                cellButtons[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
                boardPanel.add(cellButtons[i][j]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // Navigation panel
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

        // Disable tombol prev jika di history terakhir
        btnPrev.setEnabled(currentIndex < histories.size() - 1);
        // Disable tombol next jika di history pertama
        btnNext.setEnabled(currentIndex > 0);
    }
}
