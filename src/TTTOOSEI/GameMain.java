package TTTOOSEI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JFrame {
    public static final int CANVAS_WIDTH = Cell.SIZE * Board.COLS;
    public static final int CANVAS_HEIGHT = Cell.SIZE * Board.ROWS;

    private Board board;
    private DrawCanvas canvas;
    private JLabel statusBar;

    public GameMain() {
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                int rowSelected = mouseY / Cell.SIZE;
                int colSelected = mouseX / Cell.SIZE;

                if (board.currentState == State.PLAYING) {
                    if (rowSelected >= 0 && rowSelected < Board.ROWS && colSelected >= 0 && colSelected < Board.COLS
                            && board.cells[rowSelected][colSelected].content == Seed.NO_SEED) {
                        board.cells[rowSelected][colSelected].content = board.currentPlayer;
                        board.updateGame(board.currentPlayer, rowSelected, colSelected);

                        if (board.currentState == State.PLAYING) {
                            SoundEffect.EAT_FOOD.play();
                            board.currentPlayer = (board.currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        } else {
                            SoundEffect.DIE.play();
                        }
                    }
                } else {
                    board.initGame();
                    
                    SoundEffect.initGame();
                }

                repaint();
            }
        });

        statusBar = new JLabel(" ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);
        cp.add(statusBar, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("Tic Tac Toe with Sound and Images");
        setVisible(true);

        board = new Board();
        SoundEffect.initGame();

    }

    class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            board.paint(g);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameMain());
    }
}

