package TTTOOExtended;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameMain extends JPanel {
    private Board board;
    private DrawCanvas canvas;
    private JLabel statusBar;

    public GameMain() {
        setLayout(new BorderLayout());

        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(MainFrame.CANVAS_WIDTH, MainFrame.CANVAS_HEIGHT));

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                int rowSelected = mouseY / Cell.SIZE;
                int colSelected = mouseX / Cell.SIZE;

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

        add(canvas, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

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
}
