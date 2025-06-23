package TTTOOExtended.service;

import TTTOOExtended.model.GameHistory;
import TTTOOExtended.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DBService provides database access for user registration, authentication,
 * and game history storage and retrieval.
 */
public class DBService {
    private static final String URL = "jdbc:mysql://ttt-user:ttt-password@test-db-flo-w.f.aivencloud.com:25414/ttt_db?ssl-mode=REQUIRED";

    // Returns a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Registers a new user into the database
    public static boolean registerUser(String username, String password) throws SQLException {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
        }
    }

    // Authenticates a user and returns the User object if successful
    public static User login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String uname = rs.getString("username");
                return new User(id, uname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Saves a completed game history into the database
    public static void saveGameHistory(int userId, String winner, String finalState) {
        String sql = "INSERT INTO game_history (user_id, winner, final_state) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, winner);
            ps.setString(3, finalState);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieves all game histories for a specific user, ordered by latest first
    public static List<GameHistory> getGameHistoriesByUserId(int userId) {
        List<GameHistory> historyList = new ArrayList<>();
        String sql = "SELECT * FROM game_history WHERE user_id = ? ORDER BY timestamp DESC";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GameHistory history = new GameHistory(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("winner"),
                        rs.getString("final_state"),
                        rs.getString("timestamp")
                );
                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }
}
