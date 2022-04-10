package task2;

import java.util.List;
import java.util.ArrayList;

public class APICommOffline implements APIComm {
    String token = null;
    String username = null;
    boolean loanTaken = false;
    boolean shipPurchased = false;
    User user = null;
    public APICommOffline(){

    }
    public String getUsername() {
        return this.username;
    }

    public String getToken() {
        return this.token;
    }

    public User getUser() {
        int money = 0;
        if(this.loanTaken) {
            money = 1;
        }
        int ships = 0;
        if(this.shipPurchased) {
            ships = 1;
        }
        return new User(this.username, money, "00-00", ships, 0);
    }

    public boolean serverStatus() {
        return false;
    }

    public UserWrapper getInfo() {
        this.username = "Default";
        User user = getUser();
        UserWrapper userWrap = new UserWrapper(user);
        return userWrap;
    }

    public UserWrapper login(String token) {
        this.token = token;
        return getInfo();
    }

    public boolean signUp(String username) {
        this.username = username;
        return true;
    }

    public LoansWrapper viewLoans() {
        if(!this.loanTaken) {
            Loans loan = new Loans(1, false, 0, 0, "???");
            List<Loans> loans = new ArrayList<Loans>();
            loans.add(loan);
            LoansWrapper loansWrap = new LoansWrapper(loans);
            return loansWrap;
        } else {
            return null;
        }
    }

    public boolean takeLoan(String type) {
        this.loanTaken = true;
        return true;
    }

    public LoansWrapper viewActiveLoans() {
        if(this.loanTaken) {
            Loans loan = new Loans(0, false, 0, 0, "???");
            List<Loans> loans = new ArrayList<Loans>();
            loans.add(loan);
            LoansWrapper loansWrap = new LoansWrapper(loans);
            return loansWrap;
        } else {
            return null;
        }
    }

    public ShipsWrapper listAvailableShips() {
        if(this.shipPurchased) {
            return null;
        } else {
            Location loc = new Location("A", 1, "B");
            List<Location> locList = new ArrayList<Location>();
            locList.add(loc);
            Ship ship = new Ship("A Class", "A Manufacturer", 0, 0, locList, 0, "A type", 0);
            List<Ship> shipList = new ArrayList<Ship>();
            shipList.add(ship);
            ShipsWrapper shipWrap = new ShipsWrapper(shipList);
            return shipWrap;
        }
    }

    public boolean purchaseShip(String location, String type) {
        if(this.loanTaken) {
            this.shipPurchased = true;
            return true;
        } else {
            return false;
        }
    }

    public ShipInventory viewShips() {
        if(this.shipPurchased) {
            Ship ship = new Ship("A Class", "A Manufacturer", 0, 0, 0, "A type", 0);
            List<Ship> shipList = new ArrayList<Ship>();
            shipList.add(ship);
            ShipInventory shipWrap = new ShipInventory(shipList);
            return shipWrap;
        } else {
            return null;
        }
    }
}
