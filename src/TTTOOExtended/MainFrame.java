package TTTOOExtended;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final int CANVAS_WIDTH = Cell.SIZE * Board.COLS;
    public static final int CANVAS_HEIGHT = Cell.SIZE * Board.ROWS;

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JLabel userInfoLabel;

    public MainFrame() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        userInfoLabel = new JLabel("");
        userInfoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        userInfoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        userInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(userInfoLabel, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new WelcomePanel(cardLayout, mainPanel), "Welcome");
        mainPanel.add(new RegisterPanel(cardLayout, mainPanel), "Register");
        mainPanel.add(new LoginPanel(cardLayout, mainPanel, this), "Login"); // ← penting!
        mainPanel.add(new MainMenuPanel(cardLayout, mainPanel, this), "MainMenu");
        mainPanel.add(new GameMain(), "Game");

        add(mainPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);

        cardLayout.show(mainPanel, "Welcome");
    }

    public void updateUserInfoLabel() {
        User user = Session.getUser();
        if (user != null) {
            userInfoLabel.setText("Hi, " + user.getUsername() + "!");
        } else {
            userInfoLabel.setText("Not logged in");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
