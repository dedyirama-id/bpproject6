package TTTOOExtended;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterPanel extends JPanel {
    public RegisterPanel(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new GridLayout(5, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(registerButton);
        add(backButton);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty.");
                return;
            }

            try {
                boolean success = DBConnection.registerUser(username, password);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Register Successful!");
                    cardLayout.show(mainPanel, "Welcome");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Register failed: " + ex.getMessage());
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));
    }
}
