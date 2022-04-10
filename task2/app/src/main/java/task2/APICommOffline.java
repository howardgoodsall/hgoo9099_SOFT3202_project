package task2;

public class APICommOffline implements APIComm {
    String token = null;
    String username = null;
    public APICommOffline(){

    }
    public String getUsername() {
        return this.username;
    }

    public String getToken() {
        return this.token;
    }

    public User getUser() {
        return null;
    }

    public boolean serverStatus() {
        return false;
    }

    public boolean login(String token) {
        this.token = token;
        return true;
    }

    public boolean signUp(String username) {
        this.username = username;
        return true;
    }
}
