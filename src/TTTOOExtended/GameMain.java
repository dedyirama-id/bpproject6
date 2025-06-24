package TTTOOExtended;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * GameMain is the panel that handles the Tic Tac Toe gameplay.
 * It includes the game canvas, control buttons, and status indicators.
 */
public class GameMain extends JPanel {
    private final Board board;
    private final DrawCanvas canvas;
    private final JLabel statusBar;
    private final JLabel savingLabel;
    private volatile boolean isSavingInProgress = false;

    public GameMain(MainFrame mainFrame) {
        setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = getHeaderPanel(mainFrame);
        add(headerPanel, BorderLayout.NORTH);

        // Game canvas (center)
        canvas = new DrawCanvas();
        canvas.setBackground(Color.WHITE);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isSavingInProgress) return;

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
                    board.initGame();
                    SoundEffect.initGame();
                    savingLabel.setText("");
                }

                repaint();

                if (isVsAI && board.currentPlayer == Seed.NOUGHT && board.currentState == State.PLAYING) {
                    aiMove();
                }

            }
        });

        add(canvas, BorderLayout.CENTER);

        // Footer panel (status and saving info)
        JPanel footerPanel = new JPanel(new BorderLayout());

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

        // Initialize the game board with saveGame behavior override
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

    // Create header panel
    private JPanel getHeaderPanel(MainFrame mainFrame) {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        JButton resetButton = new JButton("Reset");

        backButton.addActionListener(_ -> mainFrame.getCardLayout().show(mainFrame.getMainPanel(), "MainMenu"));

        resetButton.addActionListener(_ -> {
            if (isSavingInProgress) return;
            board.initGame();
            SoundEffect.initGame();
            savingLabel.setText("");
            repaint();
        });

        headerPanel.add(backButton);
        headerPanel.add(resetButton);
        return headerPanel;
    }

    // Display saving message immediately when game ends
    private void showSavingStatus() {
        savingLabel.setText("Saving history...");
    }

    /**
     * DrawCanvas handles the rendering of the Tic Tac Toe grid and game state.
     */
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
    // AI CONFIG
    private final boolean isVsAI = true; // true = mode AI, false = PvP
    private final String aiLevel = "hard"; // "easy", "medium", "hard"

    // AI LOGIC
    private void aiMove() {
        if (board.currentState != State.PLAYING) return;

        int[] move = null;

        if ("easy".equals(aiLevel)) {
            move = randomMove();
        } else if ("medium".equals(aiLevel)) {
            move = Math.random() < 0.5 ? randomMove() : findBestMove(board.currentPlayer);
        } else {
            move = findBestMove(board.currentPlayer);
        }

        if (move != null) {
            board.cells[move[0]][move[1]].content = board.currentPlayer;
            board.updateGame(board.currentPlayer, move[0], move[1]);

            if (board.currentState == State.PLAYING) {
                SoundEffect.EAT_FOOD.play();
                board.currentPlayer = (board.currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
            } else {
                SoundEffect.DIE.play();
                showSavingStatus();
            }
            repaint();

        }
    }

    private int[] randomMove() {
        java.util.List<int[]> empty = new java.util.ArrayList<>();
        for (int r = 0; r < Board.ROWS; r++) {
            for (int c = 0; c < Board.COLS; c++) {
                if (board.cells[r][c].content == Seed.NO_SEED) {
                    empty.add(new int[]{r, c});
                }
            }
        }
        if (empty.isEmpty()) return null;
        return empty.get((int)(Math.random() * empty.size()));
    }

    private int[] findBestMove(Seed aiSeed) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;
        for (int r = 0; r < Board.ROWS; r++) {
            for (int c = 0; c < Board.COLS; c++) {
                if (board.cells[r][c].content == Seed.NO_SEED) {
                    board.cells[r][c].content = aiSeed;
                    int score = minimax(0, false, aiSeed, getOpponent(aiSeed));
                    board.cells[r][c].content = Seed.NO_SEED;
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{r, c};
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(int depth, boolean isMax, Seed aiSeed, Seed opponent) {
        State eval = evaluateBoard(board);
        if (eval == State.CROSS_WON) return (aiSeed == Seed.CROSS) ? 10 - depth : depth - 10;
        if (eval == State.NOUGHT_WON) return (aiSeed == Seed.NOUGHT) ? 10 - depth : depth - 10;
        if (eval == State.DRAW) return 0;

        int best = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int r = 0; r < Board.ROWS; r++) {
            for (int c = 0; c < Board.COLS; c++) {
                if (board.cells[r][c].content == Seed.NO_SEED) {
                    board.cells[r][c].content = isMax ? aiSeed : opponent;
                    int score = minimax(depth + 1, !isMax, aiSeed, opponent);
                    board.cells[r][c].content = Seed.NO_SEED;
                    best = isMax ? Math.max(best, score) : Math.min(best, score);
                }
            }
        }
        return best;
    }

    private Seed getOpponent(Seed player) {
        return (player == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
    }

    private State evaluateBoard(Board board) {
        for (int i = 0; i < 3; i++) {
            if (board.cells[i][0].content != Seed.NO_SEED &&
                    board.cells[i][0].content == board.cells[i][1].content &&
                    board.cells[i][1].content == board.cells[i][2].content) {
                return winnerToState(board.cells[i][0].content);
            }
            if (board.cells[0][i].content != Seed.NO_SEED &&
                    board.cells[0][i].content == board.cells[1][i].content &&
                    board.cells[1][i].content == board.cells[2][i].content) {
                return winnerToState(board.cells[0][i].content);
            }
        }

        if (board.cells[0][0].content != Seed.NO_SEED &&
                board.cells[0][0].content == board.cells[1][1].content &&
                board.cells[1][1].content == board.cells[2][2].content) {
            return winnerToState(board.cells[0][0].content);
        }

        if (board.cells[0][2].content != Seed.NO_SEED &&
                board.cells[0][2].content == board.cells[1][1].content &&
                board.cells[1][1].content == board.cells[2][0].content) {
            return winnerToState(board.cells[0][2].content);
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board.cells[row][col].content == Seed.NO_SEED) {
                    return State.PLAYING;
                }
            }
        }
        return State.DRAW;
    }

    private State winnerToState(Seed winner) {
        return (winner == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;

    }

}







