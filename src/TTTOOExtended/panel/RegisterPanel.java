package TTTOOExtended.panel;

import TTTOOExtended.service.DBService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Registration screen panel that allows new users to sign up.
 */
public class RegisterPanel extends JPanel {

    public RegisterPanel(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Heading
        JLabel heading = new JLabel("REGISTER");
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

        // Register button
        JButton registerButton = new JButton("Register");
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);

        // Back button
        JButton backButton = new JButton("◀️ Back");
        row++;
        gbc.gridy = row;
        add(backButton, gbc);

        // Register event handler
        registerButton.addActionListener(_ -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty.");
                return;
            }

            try {
                boolean success = DBService.registerUser(username, password);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Register Successful!");
                    cardLayout.show(mainPanel, "Login");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Register failed: " + ex.getMessage());
            }
        });

        // Back button event
        backButton.addActionListener(_ -> cardLayout.show(mainPanel, "Welcome"));
    }
}
