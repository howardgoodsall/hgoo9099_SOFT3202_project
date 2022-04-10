package task2;

import java.util.List;

public class ShipsWrapper {
    private List<Ship> shipListings = null;


    public ShipsWrapper(List<Ship> shipListings) {
        this.shipListings = shipListings;
    }

    public List<Ship> getShips() {
        return this.shipListings;
    }
}
