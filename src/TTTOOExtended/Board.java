package TTTOOExtended;

import java.awt.*;

public class Board {
    public static final int ROWS = 3;
    public static final int COLS = 3;

    Cell[][] cells;
    State currentState;
    Seed currentPlayer;

    public Board() {
        cells = new Cell[ROWS][COLS];
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }
        initGame();
    }

    public void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].newGame();
            }
        }
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        for (int row = 1; row < ROWS; ++row) {
            g.fillRoundRect(0, Cell.SIZE * row - 4, Cell.SIZE * COLS, 8, 8, 8);
        }
        for (int col = 1; col < COLS; ++col) {
            g.fillRoundRect(Cell.SIZE * col - 4, 0, 8, Cell.SIZE * ROWS, 8, 8);
        }
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].paint(g);
            }
        }
    }

    public void updateGame(Seed theSeed, int row, int col) {
        if (hasWon(theSeed, row, col)) {
            currentState = (theSeed == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else if (isDraw()) {
            currentState = State.DRAW;
        }

        saveGameIfEnded();
    }

    public boolean isDraw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean hasWon(Seed theSeed, int row, int col) {
        return (cells[row][0].content == theSeed && cells[row][1].content == theSeed && cells[row][2].content == theSeed
                || cells[0][col].content == theSeed && cells[1][col].content == theSeed && cells[2][col].content == theSeed
                || row == col && cells[0][0].content == theSeed && cells[1][1].content == theSeed && cells[2][2].content == theSeed
                || row + col == 2 && cells[0][2].content == theSeed && cells[1][1].content == theSeed && cells[2][0].content == theSeed);
    }

    private void saveGameIfEnded() {
        if (currentState == State.CROSS_WON || currentState == State.NOUGHT_WON || currentState == State.DRAW) {
            String winner;
            if (currentState == State.CROSS_WON) {
                winner = "X";
            } else if (currentState == State.NOUGHT_WON) {
                winner = "O";
            } else {
                winner = "Draw";
            }

            StringBuilder finalState = new StringBuilder();
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    String value = cells[row][col].content == Seed.CROSS ? "X" :
                            cells[row][col].content == Seed.NOUGHT ? "O" : "";
                    finalState.append(value);
                    if (col < 2) finalState.append(",");
                }
                if (row < 2) finalState.append(";");
            }

            DBConnection.saveGameHistory(Session.getCurrentUserId(), winner, finalState.toString());
        }
    }

}
