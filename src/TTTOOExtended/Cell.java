package TTTOOExtended;

import java.awt.*;
/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {
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
