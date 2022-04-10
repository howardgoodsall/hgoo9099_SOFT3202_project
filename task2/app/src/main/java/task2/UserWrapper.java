package task2;

public class UserWrapper {
    private User user;
    private Ship ship;
    private String token;

    public UserWrapper(User user) {
        this.user = user;
    }

    public UserWrapper(User user, Ship ship) {
        this.user = user;
        this.ship = ship;
    }

    public UserWrapper(String token, User user) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return this.user;
    }

    public Ship getShip() {
        return this.ship;
    }

    public String getToken() {
        return this.token;
    }
}
