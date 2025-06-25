package TTTOOExtended.model;

import javax.swing.*;

public class GameConfig {
    private static ImageIcon customIconX = null;
    private static ImageIcon customIconO = null;

    public static void setCustomIconX(ImageIcon icon) {
        customIconX = icon;
    }

    public static void setCustomIconO(ImageIcon icon) {
        customIconO = icon;
    }

    public static ImageIcon getCustomIconX() {
        return customIconX;
    }

    public static ImageIcon getCustomIconO() {
        return customIconO;
    }

    public static void resetIcons() {
        customIconX = null;
        customIconO = null;
    }
}
