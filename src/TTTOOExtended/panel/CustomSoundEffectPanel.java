package TTTOOExtended.panel;

import TTTOOExtended.MainFrame;
import TTTOOExtended.model.Session;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class CustomSoundEffectPanel extends JPanel {
    private final JLabel startFileLabel = new JLabel();
    private final JLabel moveFileLabel = new JLabel();
    private final JLabel endFileLabel = new JLabel();

    public CustomSoundEffectPanel(MainFrame mainFrame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titleLabel = new JLabel("Custom Sound Effects");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createVerticalStrut(15));

        add(createSoundRow("Start", "start", startFileLabel));
        add(Box.createVerticalStrut(10));
        add(createSoundRow("Move", "move", moveFileLabel));
        add(Box.createVerticalStrut(10));
        add(createSoundRow("End", "end", endFileLabel));
        add(Box.createVerticalStrut(15));

        JButton backBtn = new JButton("◀️ Back");
        backBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        backBtn.setMaximumSize(new Dimension(150, 30));
        backBtn.addActionListener(_ -> mainFrame.getCardLayout().show(mainFrame.getMainPanel(), "MainMenu"));
        add(backBtn);
        add(Box.createVerticalGlue());

        updateFileLabel("start");
        updateFileLabel("move");
        updateFileLabel("end");
    }

    private JPanel createSoundRow(String labelText, String type, JLabel fileLabel) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35)); // batas tinggi tetap

        JButton resetButton = new JButton("🔁");
        resetButton.setToolTipText("Reset " + labelText + " sound");

        JButton selectButton = new JButton(labelText + " Sound");

        fileLabel.setText("Default");
        fileLabel.setPreferredSize(new Dimension(180, 25));
        fileLabel.setHorizontalAlignment(SwingConstants.LEFT);

        resetButton.addActionListener(_ -> {
            resetSound(type);
            updateFileLabel(type);
        });

        selectButton.addActionListener(_ -> {
            selectSoundFile(type);
            updateFileLabel(type);
        });

        rowPanel.add(resetButton);
        rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        rowPanel.add(selectButton);
        rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        rowPanel.add(fileLabel);

        return rowPanel;
    }


    private void updateFileLabel(String type) {
        String path = switch (type) {
            case "start" -> Session.getCustomStartSoundPath();
            case "move" -> Session.getCustomMoveSoundPath();
            case "end" -> Session.getCustomEndSoundPath();
            default -> null;
        };

        JLabel label = switch (type) {
            case "start" -> startFileLabel;
            case "move" -> moveFileLabel;
            case "end" -> endFileLabel;
            default -> null;
        };

        if (label != null) {
            label.setText((path != null) ? new File(path).getName() : "Default");
        }
    }

    private void selectSoundFile(String type) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Audio Files", "wav", "mp3", "ogg", "aiff", "au"
        );
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            switch (type) {
                case "start" -> Session.setCustomStartSoundPath(selectedFile.getAbsolutePath());
                case "move" -> Session.setCustomMoveSoundPath(selectedFile.getAbsolutePath());
                case "end" -> Session.setCustomEndSoundPath(selectedFile.getAbsolutePath());
            }

            JOptionPane.showMessageDialog(this, "Sound for '" + type + "' set!");
        }
    }

    private void resetSound(String type) {
        switch (type) {
            case "start" -> Session.setCustomStartSoundPath(null);
            case "move" -> Session.setCustomMoveSoundPath(null);
            case "end" -> Session.setCustomEndSoundPath(null);
        }
        JOptionPane.showMessageDialog(this, "Sound for '" + type + "' reset to default.");
    }
}
