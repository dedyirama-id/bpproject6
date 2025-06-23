package TTTOOExtended.panel;

import TTTOOExtended.service.DBService;
import TTTOOExtended.MainFrame;
import TTTOOExtended.model.Session;
import TTTOOExtended.model.User;

import javax.swing.*;
import java.awt.*;

/**
 * LoginPanel handles user authentication and UI layout for the login screen.
 */
public class LoginPanel extends JPanel {

    public LoginPanel(CardLayout cardLayout, JPanel mainPanel, MainFrame mainFrame) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Heading
        JLabel heading = new JLabel("LOGIN");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setHorizontalAlignment(SwingConstants.LEFT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(heading, gbc);

        gbc.gridwidth = 1;
        int row = 1;

        // Username input
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(usernameLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(usernameField, gbc);

        // Password input
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        // Back button
        JButton backButton = new JButton("◀️ Back");
        row++;
        gbc.gridy = row;
        add(backButton, gbc);

        // Login action
        loginButton.addActionListener(_ -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty.");
                return;
            }

            User loggedUser = DBService.login(username, password);
            if (loggedUser != null) {
                Session.setUser(loggedUser);
                mainFrame.updateUserInfoLabel();
                cardLayout.show(mainPanel, "MainMenu");
            } else {
                JOptionPane.showMessageDialog(this, "Username or password incorrect.");
            }
        });

        // Back action
        backButton.addActionListener(_ -> cardLayout.show(mainPanel, "Welcome"));
    }
}
