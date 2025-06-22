package TTTOODesign;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TTTOODesign {

    // Tambahkan enum Seed
    public enum Seed {
        NO_SEED, CROSS, NOUGHT
    }

    // Tambahkan enum State
    public enum State {
        PLAYING, DRAW, CROSS_WON, NOUGHT_WON
    }

    public static class Cell {
        public static final int SIZE = 120;
        public static final int PADDING = SIZE / 5;
        public static final int SEED_SIZE = SIZE - PADDING * 2;
        public static final int SEED_STROKE_WIDTH = 8;

        Seed content;
        int row, col;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
            content = Seed.NO_SEED;
        }

        public void newGame() {
            content = Seed.NO_SEED;
        }

        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(SEED_STROKE_WIDTH,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int x1 = col * SIZE + PADDING;
            int y1 = row * SIZE + PADDING;
            if (content == Seed.CROSS) {
                g2d.setColor(GameMain.COLOR_CROSS);
                int x2 = (col + 1) * SIZE - PADDING;
                int y2 = (row + 1) * SIZE - PADDING;
                g2d.drawLine(x1, y1, x2, y2);
                g2d.drawLine(x2, y1, x1, y2);
            } else if (content == Seed.NOUGHT) {
                g2d.setColor(GameMain.COLOR_NOUGHT);
                g2d.drawOval(x1, y1, SEED_SIZE, SEED_SIZE);
            }
        }
    }

    public static class Board {
        public static final int ROWS = 3;
        public static final int COLS = 3;
        public static final int CANVAS_WIDTH = Cell.SIZE * COLS;
        public static final int CANVAS_HEIGHT = Cell.SIZE * ROWS;
        public static final int GRID_WIDTH = 8;
        public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
        public static final Color COLOR_GRID = Color.LIGHT_GRAY;
        public static final int Y_OFFSET = 1;

        Cell[][] cells;

        public Board() {
            initGame();
        }

        public void initGame() {
            cells = new Cell[ROWS][COLS];
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    cells[row][col] = new Cell(row, col);
                }
            }
        }

        public void newGame() {
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    cells[row][col].newGame();
                }
            }
        }

        public State stepGame(Seed player, int selectedRow, int selectedCol) {
            cells[selectedRow][selectedCol].content = player;

            if (cells[selectedRow][0].content == player
                    && cells[selectedRow][1].content == player
                    && cells[selectedRow][2].content == player
                    || cells[0][selectedCol].content == player
                    && cells[1][selectedCol].content == player
                    && cells[2][selectedCol].content == player
                    || selectedRow == selectedCol
                    && cells[0][0].content == player
                    && cells[1][1].content == player
                    && cells[2][2].content == player
                    || selectedRow + selectedCol == 2
                    && cells[0][2].content == player
                    && cells[1][1].content == player
                    && cells[2][0].content == player) {
                return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
            } else {
                for (int row = 0; row < ROWS; ++row) {
                    for (int col = 0; col < COLS; ++col) {
                        if (cells[row][col].content == Seed.NO_SEED) {
                            return State.PLAYING;
                        }
                    }
                }
                return State.DRAW;
            }
        }

        public void paint(Graphics g) {
            g.setColor(COLOR_GRID);
            for (int row = 1; row < ROWS; ++row) {
                g.fillRoundRect(0, Cell.SIZE * row - GRID_WIDTH_HALF,
                        CANVAS_WIDTH - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < COLS; ++col) {
                g.fillRoundRect(Cell.SIZE * col - GRID_WIDTH_HALF, 0 + Y_OFFSET,
                        GRID_WIDTH, CANVAS_HEIGHT - 1, GRID_WIDTH, GRID_WIDTH);
            }

            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    cells[row][col].paint(g);
                }
            }
        }
    }

    public static class GameMain extends JPanel {
        private static final long serialVersionUID = 1L;

        public static final String TITLE = "Tic Tac Toe";
        public static final Color COLOR_BG = Color.WHITE;
        public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
        public static final Color COLOR_CROSS = new Color(239, 105, 80);
        public static final Color COLOR_NOUGHT = new Color(64, 154, 225);
        public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

        private Board board;
        private State currentState;
        private Seed currentPlayer;
        private JLabel statusBar;

        public GameMain() {
            super.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    int row = mouseY / Cell.SIZE;
                    int col = mouseX / Cell.SIZE;

                    if (currentState == State.PLAYING) {
                        if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                                && board.cells[row][col].content == Seed.NO_SEED) {
                            currentState = board.stepGame(currentPlayer, row, col);
                            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        }
                    } else {
                        newGame();
                    }
                    repaint();
                }
            });

            statusBar = new JLabel();
            statusBar.setFont(FONT_STATUS);
            statusBar.setBackground(COLOR_BG_STATUS);
            statusBar.setOpaque(true);
            statusBar.setPreferredSize(new Dimension(300, 30));
            statusBar.setHorizontalAlignment(JLabel.LEFT);
            statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

            setLayout(new BorderLayout());
            add(statusBar, BorderLayout.PAGE_END);
            setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
            setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

            initGame();
            newGame();
        }

        public void initGame() {
            board = new Board();
        }

        public void newGame() {
            for (int row = 0; row < Board.ROWS; ++row) {
                for (int col = 0; col < Board.COLS; ++col) {
                    board.cells[row][col].content = Seed.NO_SEED;
                }
            }
            currentPlayer = Seed.CROSS;
            currentState = State.PLAYING;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(COLOR_BG);
            board.paint(g);

            if (currentState == State.PLAYING) {
                statusBar.setForeground(Color.BLACK);
                statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
            } else if (currentState == State.DRAW) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("It's a Draw! Click to play again.");
            } else if (currentState == State.CROSS_WON) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("'X' Won! Click to play again.");
            } else if (currentState == State.NOUGHT_WON) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("'O' Won! Click to play again.");
            }
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame(TITLE);
                frame.setContentPane(new GameMain());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
        }
    }
}

