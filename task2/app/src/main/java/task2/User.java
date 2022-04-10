package task2;

public class User {
    private int credits;
    private String joinedAt;
    private int shipCount;
    private int structureCount;
    private String username;

    public User(String username, int credits, String joinedAt, int shipCount, int structureCount) {
        this.username = username;
        this.credits = credits;
        this.joinedAt = joinedAt;
        this.shipCount = shipCount;
        this.structureCount = structureCount;
    }

    public User(int credits) {
        this.credits = credits;
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
