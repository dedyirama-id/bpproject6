package TTTOOExtended.model;

/**
 * Represents a saved game history entry for a user.
 * Contains information about game result and board state.
 */
public class GameHistory {
    public int id;
    public int userId;
    public String winner;
    public String finalState;
    public String timestamp;

    // Constructor to initialize all fields
    public GameHistory(int id, int userId, String winner, String finalState, String timestamp) {
        this.id = id;
        this.userId = userId;
        this.winner = winner;
        this.finalState = finalState;
        this.timestamp = timestamp;
    }

    // Parses the finalState string into a 2D board array
    public String[][] parseBoard() {
        String[][] board = new String[3][3];
        String[] rows = finalState.split(";");

        for (int i = 0; i < 3; i++) {
            if (i < rows.length) {
                String[] cols = rows[i].split(",");
                for (int j = 0; j < 3; j++) {
                    board[i][j] = (j < cols.length) ? cols[j] : "";
                }
            } else {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = "";
                }
            }
        }

        return board;
    }
}
