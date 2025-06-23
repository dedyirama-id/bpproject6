package TTTOOExtended.panel;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    public WelcomePanel(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));

        JButton registerButton = new JButton("🔺 Register");
        JButton loginButton = new JButton("🔻 Login");

        Dimension buttonSize = new Dimension(200, 60);
        registerButton.setMaximumSize(buttonSize);
        loginButton.setMaximumSize(buttonSize);

        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(registerButton);
        add(Box.createVerticalStrut(15));
        add(loginButton);
        add(Box.createVerticalGlue());

        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "Register"));
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
    }
}
