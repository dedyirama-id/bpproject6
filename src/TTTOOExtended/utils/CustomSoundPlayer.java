package TTTOOExtended.utils;

import TTTOOExtended.SoundEffect;
import TTTOOExtended.model.Session;

import javax.sound.sampled.*;
import java.io.File;

public class CustomSoundPlayer {

    private static void playFromFile(String filePath) {
        if (filePath == null) return;

        new Thread(() -> {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (Exception e) {
                System.err.println("Failed to play custom sound: " + e.getMessage());
            }
        }).start();
    }

    public static void playStartSound() {
        String path = Session.getCustomStartSoundPath();
        if (path != null) {
            playFromFile(path);
        } else {
            SoundEffect.EXPLODE.play();  // default start sound
        }
    }

    public static void playMoveSound() {
        String path = Session.getCustomMoveSoundPath();
        if (path != null) {
            playFromFile(path);
        } else {
            SoundEffect.EAT_FOOD.play();  // default move sound
        }
    }

    public static void playEndSound() {
        String path = Session.getCustomEndSoundPath();
        if (path != null) {
            playFromFile(path);
        } else {
            SoundEffect.DIE.play();  // default end sound
        }
    }
}
