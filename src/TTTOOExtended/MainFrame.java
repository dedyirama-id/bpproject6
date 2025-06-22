package TTTOOExtended;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final int CANVAS_WIDTH = Cell.SIZE * Board.COLS;
    public static final int CANVAS_HEIGHT = Cell.SIZE * Board.ROWS;

    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public MainFrame() {
        setTitle("Tic Tac Toe");
        setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inisialisasi layout dan panel utama
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Buat dan tambahkan panel-panel
        MainMenuPanel mainMenu = new MainMenuPanel(cardLayout, mainPanel);
        GameMain gamePanel = new GameMain();  // versi panel, bukan JFrame

        mainPanel.add(mainMenu, "MainMenu");
        mainPanel.add(gamePanel, "Game");

        add(mainPanel);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
