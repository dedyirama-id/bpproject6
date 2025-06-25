package TTTOOExtended.utils;

import TTTOOExtended.SoundEffect;
import TTTOOExtended.model.Session;

import javax.sound.sampled.*;
import java.io.File;

public class CustomSoundPlayer {
    private static Clip startClip = null;
    private static Clip moveClip = null;
    private static Clip endClip = null;

    private static Clip loadClipOnce(String filePath, Clip existingClip) {
        if (filePath == null) return null;

        try {
            if (existingClip == null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                return clip;
            }
        } catch (Exception e) {
            System.err.println("Failed to load custom sound: " + e.getMessage());
        }
        return existingClip;
    }

    private static void playClip(Clip clip) {
        if (clip == null) return;

        new Thread(() -> {
            if (clip.isRunning()) clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }).start();
    }

    public static void playStartSound() {
        String path = Session.getCustomStartSoundPath();
        if (path != null) {
            startClip = loadClipOnce(path, startClip);
            playClip(startClip);
        } else {
            SoundEffect.START.play();
        }
    }

    public static void playMoveSound() {
        String path = Session.getCustomMoveSoundPath();
        if (path != null) {
            moveClip = loadClipOnce(path, moveClip);
            playClip(moveClip);
        } else {
            SoundEffect.MOVE.play();
        }
    }

    public static void playEndSound() {
        String path = Session.getCustomEndSoundPath();
        if (path != null) {
            endClip = loadClipOnce(path, endClip);
            playClip(endClip);
        } else {
            SoundEffect.END.play();
        }
    }
}
