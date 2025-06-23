package TTTOOExtended.panel;

import javax.swing.*;
import java.awt.*;

/**
 * Welcome screen panel offering navigation to Register or Login panels.
 */
public class WelcomePanel extends JPanel {

    public WelcomePanel(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));

        // Buttons for navigation
        JButton registerButton = new JButton("🔺 Register");
        JButton loginButton = new JButton("🔻 Login");

        // Configure button dimensions and alignment
        Dimension buttonSize = new Dimension(200, 60);
        registerButton.setMaximumSize(buttonSize);
        loginButton.setMaximumSize(buttonSize);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Layout structure
        add(Box.createVerticalGlue());
        add(registerButton);
        add(Box.createVerticalStrut(15));
        add(loginButton);
        add(Box.createVerticalGlue());

        // Navigation logic
        registerButton.addActionListener(_ -> cardLayout.show(mainPanel, "Register"));
        loginButton.addActionListener(_ -> cardLayout.show(mainPanel, "Login"));
    }
}
