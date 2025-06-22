package TTTOOExtended;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final int CANVAS_WIDTH = Cell.SIZE * Board.COLS;
    public static final int CANVAS_HEIGHT = Cell.SIZE * Board.ROWS;

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Tambahkan semua panel
        mainPanel.add(new WelcomePanel(cardLayout, mainPanel), "Welcome");
        mainPanel.add(new RegisterPanel(cardLayout, mainPanel), "Register");
        mainPanel.add(new MainMenuPanel(cardLayout, mainPanel), "MainMenu");
        mainPanel.add(new GameMain(), "Game");

        add(mainPanel);
        pack();
        setVisible(true);

        cardLayout.show(mainPanel, "Welcome");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
