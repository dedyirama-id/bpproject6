package TTTOOExtended;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        lblTimestamp = new JLabel("Waktu: ");
        lblWinner = new JLabel("Pemenang: ");
        topPanel.add(lblTimestamp);
        topPanel.add(lblWinner);
        add(topPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        cellButtons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cellButtons[i][j] = new JButton();
                cellButtons[i][j].setEnabled(false);
                boardPanel.add(cellButtons[i][j]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

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
        histories = DBConnection.getGameHistoriesByUserId(Session.getCurrentUserId());
        if (!histories.isEmpty()) {
            currentIndex = 0;
            loadHistory();
        } else {
            lblTimestamp.setText("Tidak ada riwayat.");
            lblWinner.setText("");
        }
    }

    private void loadHistory() {
        GameHistory game = histories.get(currentIndex);
        lblTimestamp.setText("Waktu: " + game.timestamp);
        lblWinner.setText("Pemenang: " + game.winner);

        String[][] board = game.parseBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cellButtons[i][j].setText(board[i][j]);
            }
        }
    }
}
