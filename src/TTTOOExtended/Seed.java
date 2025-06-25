package TTTOOExtended;

import TTTOOExtended.model.GameConfig;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
/**
 * This enum is used by:
 * 1. Player: takes value of CROSS or NOUGHT
 * 2. Cell content: takes value of CROSS, NOUGHT, or NO_SEED.
 *
 * We also attach a display image icon (text or image) for the items.
 *   and define the related variable/constructor/getter.
 * To draw the image:
 *   g.drawImage(content.getImage(), x, y, width, height, null);
 *
 * Ideally, we should define two enums with inheritance, which is,
 *  however, not supported.
 */
public enum Seed {   // to save as "Seed.java"
    CROSS("X", "images/cross.png"),   // displayName, imageFilename
    NOUGHT("O", "images/nought.png"),
    NO_SEED(" ", null);

    // Private variables
    private final String displayName;
    private final Image defaultImg;

    // Constructor (must be private)
    private Seed(String name, String imageFilename) {
        this.displayName = name;
        Image tempImg = null;

        if (imageFilename != null) {
            URL imgURL = getClass().getClassLoader().getResource(imageFilename);
            if (imgURL != null) {
                tempImg = new ImageIcon(imgURL).getImage();
                //System.out.println(icon);  // debugging
            } else {
                System.err.println("Couldn't find file " + imageFilename);
            }
        }
        this.defaultImg = tempImg;
    }

    // Public getters
    public String getDisplayName() {
        return displayName;
    }
    public Image getImage() {
        if (this == CROSS) {
            ImageIcon custom = GameConfig.getCustomIconX();
            return (custom != null) ? custom.getImage() : defaultImg;
        } else if (this == NOUGHT) {
            ImageIcon custom = GameConfig.getCustomIconO();
            return (custom != null) ? custom.getImage() : defaultImg;
        }
        return null;
    }
}