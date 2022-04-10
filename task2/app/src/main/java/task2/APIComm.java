package task2;


public interface APIComm {
    public boolean serverStatus();
    public String getUsername();
    public String getToken();
    public User getUser();
    public boolean login(String token);
    public boolean signUp(String username);
}
