package TTTOOExtended.panel;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {

    public WelcomePanel(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new GridBagLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        contentPanel.setOpaque(false);

        // Title
        JLabel title = new JLabel("WELCOME!");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // subtitile
        JLabel subtitle = new JLabel("# Tic Tac Toe Game");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setForeground(Color.DARK_GRAY);

        // Buttons
        JButton registerButton = new JButton("🔺 Register");
        JButton loginButton = new JButton("🔻 Login");

        Dimension buttonSize = new Dimension(200, 50);
        registerButton.setMaximumSize(buttonSize);
        loginButton.setMaximumSize(buttonSize);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Layout
        contentPanel.add(subtitle);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(25));
        contentPanel.add(registerButton);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(loginButton);

        add(contentPanel);

        // Navigation
        registerButton.addActionListener(_ -> cardLayout.show(mainPanel, "Register"));
        loginButton.addActionListener(_ -> cardLayout.show(mainPanel, "Login"));
    }
}
