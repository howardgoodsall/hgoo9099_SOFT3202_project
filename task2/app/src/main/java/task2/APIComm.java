package task2;

import java.util.List;

public interface APIComm {
    public boolean serverStatus();
    public String getUsername();
    public String getToken();
    public User getUser();
    public UserWrapper getInfo();
    public UserWrapper login(String token);
    public boolean signUp(String username);
    public LoansWrapper viewLoans();
    public boolean takeLoan(String type);
    public LoansWrapper viewActiveLoans();
    public ShipsWrapper listAvailableShips();
    public boolean purchaseShip(String location, String type);
    public ShipInventory viewShips();
}
