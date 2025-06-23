package TTTOOExtended.model;

public class Session {
    private static User currentUser;

    public static void setUser(User user) {
        currentUser = user;
    }

    public static User getUser() {
        return currentUser;
    }

    public static int getCurrentUserId() {
        return currentUser.getId();
    }

    public static void clear() {
        currentUser = null;
    }
}

