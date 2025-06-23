package TTTOOExtended.model;

/**
 * Represents a user in the system, holding ID and username information.
 */
public class User {
    private final int id;
    private final String username;

    // Constructor
    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    // Returns the user's ID
    public int getId() {
        return id;
    }

    // Returns the user's username
    public String getUsername() {
        return username;
    }
}
