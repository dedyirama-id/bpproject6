package TTTOOExtended;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginPanel extends JPanel {
    public LoginPanel(CardLayout cardLayout, JPanel mainPanel, MainFrame mainFrame) {
        setLayout(new GridLayout(5, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(backButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty.");
                return;
            }

            User loggedUser = DBConnection.login(username, password);
            if (loggedUser != null) {
                Session.setUser(loggedUser);
                mainFrame.updateUserInfoLabel();
                cardLayout.show(mainPanel, "MainMenu");
            } else {
                JOptionPane.showMessageDialog(this, "Username or password incorrect.");
            }

        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));
    }
}
