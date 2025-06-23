package TTTOOExtended;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://ttt-user:ttt-password@test-db-flo-w.f.aivencloud.com:25414/ttt_db?ssl-mode=REQUIRED";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static boolean registerUser(String username, String password) throws SQLException {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
        }
    }

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
}
