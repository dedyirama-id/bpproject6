package TTTOOExtended;

import TTTOOExtended.model.Session;
import TTTOOExtended.service.DBService;

import java.awt.*;

/**
 * Represents the 3x3 Tic Tac Toe board, containing game logic,
 * cell management, state tracking, and game result handling.
 */
public class Board {
    public static final int ROWS = 3;
    public static final int COLS = 3;

    Cell[][] cells;
    State currentState;
    Seed currentPlayer;

    public Board() {
        cells = new Cell[ROWS][COLS];

        // Initialize each cell
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }

        initGame();
    }

    // Start or reset the game
    public void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].newGame();
            }
        }
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
    }

    // Draws the grid and cells dynamically based on panel size
    public void paint(Graphics g, int width, int height) {
        int cellWidth = width / COLS;
        int cellHeight = height / ROWS;

        g.setColor(Color.BLACK);

        // Draw horizontal lines
        for (int row = 1; row < ROWS; ++row) {
            int y = row * cellHeight;
            g.fillRoundRect(0, y - 2, width, 4, 8, 8);
        }

        // Draw vertical lines
        for (int col = 1; col < COLS; ++col) {
            int x = col * cellWidth;
            g.fillRoundRect(x - 2, 0, 4, height, 8, 8);
        }

        // Draw all cells
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].paint(g, cellWidth, cellHeight);
            }
        }
    }

    // Processes a player's move and updates the game state
    public void updateGame(Seed theSeed, int row, int col) {
        if (hasWon(theSeed, row, col)) {
            currentState = (theSeed == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else if (isDraw()) {
            currentState = State.DRAW;
        }

        // Save result if game ended
        if (currentState != State.PLAYING) {
            new Thread(this::saveGame).start();
        }
    }

    // Checks for a draw (all cells filled)
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

    // Checks if the current move leads to a win
    public boolean hasWon(Seed theSeed, int row, int col) {
        return (
                cells[row][0].content == theSeed &&
                        cells[row][1].content == theSeed &&
                        cells[row][2].content == theSeed ||

                        cells[0][col].content == theSeed &&
                                cells[1][col].content == theSeed &&
                                cells[2][col].content == theSeed ||

                        row == col &&
                                cells[0][0].content == theSeed &&
                                cells[1][1].content == theSeed &&
                                cells[2][2].content == theSeed ||

                        row + col == 2 &&
                                cells[0][2].content == theSeed &&
                                cells[1][1].content == theSeed &&
                                cells[2][0].content == theSeed
        );
    }

    // Saves the current game result to the database
    protected void saveGame() {
        String winner;
        if (currentState == State.CROSS_WON) {
            winner = "X";
        } else if (currentState == State.NOUGHT_WON) {
            winner = "O";
        } else {
            winner = "Draw";
        }

        StringBuilder finalState = new StringBuilder();

        // Serialize board state
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                String value = cells[row][col].content == Seed.CROSS ? "X"
                        : cells[row][col].content == Seed.NOUGHT ? "O" : "";
                finalState.append(value);
                if (col < 2) finalState.append(",");
            }
            if (row < 2) finalState.append(";");
        }

        DBService.saveGameHistory(Session.getCurrentUserId(), winner, finalState.toString());
    }
}
