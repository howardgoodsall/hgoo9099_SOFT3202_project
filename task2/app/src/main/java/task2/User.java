package task2;

public class User {
    private int credits;
    private String joinedAt;
    private int shipCount;
    private int structureCount;
    private String username;

    public User(String username, int credits, String joinedAt, int shipCount, int structureCount) {
        this.credits = credits;
        this.joinedAt = joinedAt;
        this.shipCount = shipCount;
        this.structureCount = structureCount;
        this.username = username;
    }

    public int getCredits() {
        return this.credits;
    }

    public String getJoinedAt() {
        return this.joinedAt;
    }

    public int getShipCount() {
        return this.shipCount;
    }

    public int getStructureCount() {
        return this.structureCount;
    }

    public String getUsername() {
        return this.username;
    }
}

public class UserWrapper {
    private User user;

    public UserWrapper(User user) {
        
    }

    public User getUser() {
        return this.user;
    }
}
