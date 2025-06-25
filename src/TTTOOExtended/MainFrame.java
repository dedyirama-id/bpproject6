package TTTOOExtended;

import TTTOOExtended.model.GameHistory;
import TTTOOExtended.model.Session;
import TTTOOExtended.model.User;
import TTTOOExtended.panel.*;
import TTTOOExtended.service.DBService;

import javax.swing.*;
import java.awt.*;

/**
 * MainFrame serves as the primary window for the Tic Tac Toe application.
 * It manages different screens using a CardLayout and displays the current user's info.
 */
public class MainFrame extends JFrame {
    public boolean isVsAI = true;
    public String aiLevel = "hard";
    public String playerSeed = "X";

    private GameMain gamePanel = null;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final JLabel userInfoLabel;

    public MainFrame() {
        // Frame configuration
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);
        setSize(500, 500);
        setLocationRelativeTo(null);

        // Top label for displaying user information
        userInfoLabel = new JLabel("");
        userInfoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        userInfoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        userInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(userInfoLabel, BorderLayout.NORTH);

        // Main panel with CardLayout to manage different views
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Tambahkan seedEnum
        Seed seedEnum = "X".equals(playerSeed) ? Seed.CROSS : Seed.NOUGHT;

        // Add all panels to the main container
        mainPanel.add(new WelcomePanel(cardLayout, mainPanel), "Welcome");
        mainPanel.add(new RegisterPanel(cardLayout, mainPanel), "Register");
        mainPanel.add(new LoginPanel(cardLayout, mainPanel, this), "Login");
        mainPanel.add(new MainMenuPanel(this), "MainMenu");
        mainPanel.add(new GameMain(this, isVsAI, aiLevel, seedEnum ), "Game");

        // Add main panel to the center of the frame
        add(mainPanel, BorderLayout.CENTER);

        // Show the frame and default to Welcome screen
        setVisible(true);
        cardLayout.show(mainPanel, "Welcome");
    }


    // Updates the user info label at the top of the screen
    public void updateUserInfoLabel() {
        User user = Session.getUser();
        if (user != null) {
            userInfoLabel.setText("Hi, " + user.getUsername() + "!");
        } else {
            userInfoLabel.setText("Not logged in");
        }
    }

    // Application entry point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

    // Show history panel
    public void showHistoryPanelForUser(int userId) {
        GameHistory[] histories = DBService.getGameHistoriesByUserId(userId);
        HistoryPanel historyPanel = new HistoryPanel(this, histories);

        mainPanel.add(historyPanel, "History");
        cardLayout.show(mainPanel, "History");
    }

    // Getters for CardLayout and main panel
    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void startGame(boolean isVsAI, String aiLevel, String playerSeedString) {
        this.isVsAI = isVsAI;
        this.aiLevel = aiLevel;
        this.playerSeed = "X"; // default
        if (gamePanel != null) {
            mainPanel.remove(gamePanel);
        }
            Seed seedEnum = "X".equals(playerSeedString) ? Seed.CROSS : Seed.NOUGHT;
            gamePanel = new GameMain(this, isVsAI, aiLevel, seedEnum);
            mainPanel.add(gamePanel, "Game");
            cardLayout.show(mainPanel, "Game");
        }
    }