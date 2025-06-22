package TTTOOExtended;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    public WelcomePanel(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new GridLayout(3, 1, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));

        JButton registerButton = new JButton("Register");

        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "Register"));

        add(registerButton);
    }
}
