package TTTOOExtended.model;

/**
 * Represents the current user session in the application.
 * This class stores the active logged-in user globally.
 */
public class Session {
    private static User currentUser;

    private static String customStartSoundPath;
    private static String customMoveSoundPath;
    private static String customEndSoundPath;

    public static void setCustomStartSoundPath(String path) {
        customStartSoundPath = path;
    }

    public static void setCustomMoveSoundPath(String path) {
        customMoveSoundPath = path;
    }

    public static void setCustomEndSoundPath(String path) {
        customEndSoundPath = path;
    }

    public static String getCustomStartSoundPath() {
        return customStartSoundPath;
    }

    public static String getCustomMoveSoundPath() {
        return customMoveSoundPath;
    }

    public static String getCustomEndSoundPath() {
        return customEndSoundPath;
    }


    public static void setUser(User user) {
        currentUser = user;
    }

    public static User getUser() {
        return currentUser;
    }

    public static int getCurrentUserId() {
        return currentUser.getId();
    }
}
