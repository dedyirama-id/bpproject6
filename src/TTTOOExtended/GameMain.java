package TTTOOExtended;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameMain extends JPanel {
    private Board board;
    private DrawCanvas canvas;
    private JLabel statusBar;
    private JLabel savingLabel;
    private volatile boolean isSavingInProgress = false;

    public GameMain(MainFrame mainFrame) {
        setLayout(new BorderLayout());

        // Header panel (Back + Reset)
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        JButton resetButton = new JButton("Reset");

        backButton.addActionListener(e -> {
            mainFrame.getCardLayout().show(mainFrame.getMainPanel(), "MainMenu");
        });

        resetButton.addActionListener(e -> {
            if (isSavingInProgress) return;

            board.initGame();
            SoundEffect.initGame();
            savingLabel.setText("");
            repaint();
        });


        headerPanel.add(backButton);
        headerPanel.add(resetButton);
        add(headerPanel, BorderLayout.NORTH);

        // Canvas (tengah)
        canvas = new DrawCanvas();
        canvas.setBackground(Color.WHITE);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isSavingInProgress) return; // ⛔ blok input saat saving

                int mouseX = e.getX();
                int mouseY = e.getY();

                int cellWidth = canvas.getWidth() / Board.COLS;
                int cellHeight = canvas.getHeight() / Board.ROWS;

                int rowSelected = mouseY / cellHeight;
                int colSelected = mouseX / cellWidth;

                if (board.currentState == State.PLAYING) {
                    if (rowSelected >= 0 && rowSelected < Board.ROWS &&
                            colSelected >= 0 && colSelected < Board.COLS &&
                            board.cells[rowSelected][colSelected].content == Seed.NO_SEED) {

                        board.cells[rowSelected][colSelected].content = board.currentPlayer;
                        board.updateGame(board.currentPlayer, rowSelected, colSelected);

                        if (board.currentState == State.PLAYING) {
                            SoundEffect.EAT_FOOD.play();
                            board.currentPlayer = (board.currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        } else {
                            SoundEffect.DIE.play();
                            showSavingStatus();
                        }
                    }
                } else if (!isSavingInProgress) {
                    // restart game (hanya jika tidak sedang menyimpan)
                    board.initGame();
                    SoundEffect.initGame();
                    savingLabel.setText(""); // ✅ hapus "History saved!" saat mulai ulang
                }

                repaint();
            }
        });

        add(canvas, BorderLayout.CENTER);

        // Footer (status bar)
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BorderLayout());

        statusBar = new JLabel(" ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

        savingLabel = new JLabel(" ");
        savingLabel.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        savingLabel.setForeground(Color.GRAY);
        savingLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        savingLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 10));

        footerPanel.add(statusBar, BorderLayout.WEST);
        footerPanel.add(savingLabel, BorderLayout.EAST);

        add(footerPanel, BorderLayout.SOUTH);

        board = new Board() {
            @Override
            protected void saveGame() {
                isSavingInProgress = true;
                SwingUtilities.invokeLater(() -> savingLabel.setText("Saving history..."));

                new Thread(() -> {
                    super.saveGame();

                    SwingUtilities.invokeLater(() -> {
                        savingLabel.setText("History saved!");
                        isSavingInProgress = false;
                    });
                }).start();
            }
        };

        SoundEffect.initGame();
    }

    private void showSavingStatus() {
        // dipanggil saat game selesai
        savingLabel.setText("Saving history...");
        // Penundaan "History saved!" dilakukan di saveGame()
    }

    class DrawCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int width = getWidth();
            int height = getHeight();
            board.paint(g, width, height);

            if (board.currentState == State.PLAYING) {
                statusBar.setText(board.currentPlayer == Seed.CROSS ? "X's Turn" : "O's Turn");
            } else if (board.currentState == State.DRAW) {
                statusBar.setText("It's a Draw! Click to play again.");
            } else if (board.currentState == State.CROSS_WON) {
                statusBar.setText("'X' Won! Click to play again.");
            } else if (board.currentState == State.NOUGHT_WON) {
                statusBar.setText("'O' Won! Click to play again.");
            }
        }
    }
}
