package TTTOOExtended;

import java.awt.*;

/**
 * Represents a single cell in the Tic Tac Toe board.
 * Each cell holds its coordinates and current content (CROSS, NOUGHT, or empty).
 */
public class Cell {
    Seed content;
    int row, col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        content = Seed.NO_SEED;
    }

    // Resets the cell content to empty
    public void newGame() {
        content = Seed.NO_SEED;
    }

    // Renders the seed symbol inside the cell
    public void paint(Graphics g, int cellWidth, int cellHeight) {
        int paddingX = cellWidth / 5;
        int paddingY = cellHeight / 5;
        int x1 = col * cellWidth + paddingX;
        int y1 = row * cellHeight + paddingY;
        int symbolWidth = cellWidth - 2 * paddingX;
        int symbolHeight = cellHeight - 2 * paddingY;

        if (content == Seed.CROSS || content == Seed.NOUGHT) {
            g.drawImage(content.getImage(), x1, y1, symbolWidth, symbolHeight, null);
        }
    }
}
