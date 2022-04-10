package task2;

import java.util.List;

public class ShipInventory {
    private List<Ship> ships;

    public ShipInventory(List<Ship> ships) {
        this.ships = ships;
    }

    public List<Ship> getShips() {
        return this.ships;
    }
}
