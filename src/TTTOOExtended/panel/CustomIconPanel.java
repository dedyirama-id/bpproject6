package TTTOOExtended.panel;

import TTTOOExtended.MainFrame;
import TTTOOExtended.model.GameConfig;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CustomIconPanel extends JPanel {
    private JLabel xIconLabel;
    private JLabel oIconLabel;
    private ImageIcon iconX;
    private ImageIcon iconO;

    public CustomIconPanel(MainFrame mainFrame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel titleLabel = new JLabel("🎨 Choose Custom Icons");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton chooseXButton = new JButton("Choose Icon for X");
        JButton chooseOButton = new JButton("Choose Icon for O");
        JButton backButton = new JButton("⬅️ Back");

        xIconLabel = new JLabel("No icon selected");
        oIconLabel = new JLabel("No icon selected");

        chooseXButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        chooseOButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        xIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        oIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        chooseXButton.addActionListener(_ -> {
            File file = chooseImageFile();
            if (file != null) {
                iconX = new ImageIcon(file.getAbsolutePath());
                xIconLabel.setIcon(new ImageIcon(iconX.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                xIconLabel.setText("X Icon Selected");
                // TODO: Simpan iconX di Session atau GameConfig
                GameConfig.setCustomIconX(iconX);
            }
        });

        chooseOButton.addActionListener(_ -> {
            File file = chooseImageFile();
            if (file != null) {
                iconO = new ImageIcon(file.getAbsolutePath());
                oIconLabel.setIcon(new ImageIcon(iconO.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                oIconLabel.setText("O Icon Selected");
                // TODO: Simpan iconO di Session atau GameConfig
                GameConfig.setCustomIconO(iconO);
            }
        });

        backButton.addActionListener(_ -> {
            mainFrame.getCardLayout().show(mainFrame.getMainPanel(), "MainMenu");
            mainFrame.getMainPanel().repaint();
        });

        add(titleLabel);
        add(Box.createVerticalStrut(20));
        add(chooseXButton);
        add(xIconLabel);
        add(Box.createVerticalStrut(10));
        add(chooseOButton);
        add(oIconLabel);
        add(Box.createVerticalStrut(30));
        add(backButton);
    }

    private File chooseImageFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        return (result == JFileChooser.APPROVE_OPTION) ? chooser.getSelectedFile() : null;
    }
}
