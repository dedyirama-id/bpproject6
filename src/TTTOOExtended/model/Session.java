package TTTOOExtended.model;

/**
 * Represents the current user session in the application.
 * This class stores the active logged-in user globally.
 */
public class Session {
    private static User currentUser;

    // Sets the current user
    public static void setUser(User user) {
        currentUser = user;
    }

    // Retrieves the current user
    public static User getUser() {
        return currentUser;
    }

    // Retrieves the ID of the current user
    public static int getCurrentUserId() {
        return currentUser.getId();
    }

    // Clears the current session
    public static void clear() {
        currentUser = null;
    }
}
