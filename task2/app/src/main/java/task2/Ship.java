package task2;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Ship {
    private String[] cargo;
    @SerializedName("class") private String shipClass;
    private String flightPlanId;
    private String id;
    private String location;
    private String manufacturer;
    private int maxCargo;
    private int plating;
    private int spaceAvailable;
    private List<Location> purchaseLocations;
    private int speed;
    private String type;
    private int weapons;
    private int x;
    private int y;

    public Ship(String shipClass, String manufacturer, int maxCargo, int plating,
        int speed, String type, int weapons) {
            this.shipClass = shipClass;
            this.manufacturer = manufacturer;
            this.maxCargo = maxCargo;
            this.purchaseLocations = null;
            this.plating = plating;
            this.speed = speed;
            this.type = type;
            this.weapons = weapons;
    }

    public Ship(String shipClass, String manufacturer, int maxCargo, int plating,
        List<Location> purchaseLocations, int speed, String type, int weapons) {//With purchaseLocations
            this.shipClass = shipClass;
            this.manufacturer = manufacturer;
            this.maxCargo = maxCargo;
            this.purchaseLocations = purchaseLocations;
            this.plating = plating;
            this.speed = speed;
            this.type = type;
            this.weapons = weapons;
    }

    public Ship(String[] cargo, String shipClass, String flightPlanId,
        String id, String location, String manufacturer, int maxCargo,
        int plating, int spaceAvailable, int speed, String type, int weapons,
        int x, int y) {//With cargo, id, flightPlanId, location, spaceAvailable, x & y
            this.cargo = cargo;
            this.shipClass = shipClass;
            this.flightPlanId = flightPlanId;
            this.id = id;
            this.location = location;
            this.manufacturer = manufacturer;
            this.maxCargo = maxCargo;
            this.purchaseLocations = null;
            this.plating = plating;
            this.spaceAvailable = spaceAvailable;
            this.speed = speed;
            this.type = type;
            this.weapons = weapons;
            this.x = x;
            this.y = y;
    }

    public String getShipClass() {
        return this.shipClass;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public int getMaxCargo() {
        return this.maxCargo;
    }

    public List<Location> getPurchaseLocations() {
        return this.purchaseLocations;
    }

    public int getPlating() {
        return this.plating;
    }

    public int getSpeed() {
        return this.speed;
    }

    public String getType() {
        return this.type;
    }

    public int getWeapons() {
        return this.weapons;
    }

    public String[] getCargo() {
        return this.cargo;
    }

    public String getFlightPlanId() {
        return this.flightPlanId;
    }

    public String getId() {
        return this.id;
    }

    public String getLocation() {
        return this.location;
    }

    public int getSpaceAvailable() {
        return this.spaceAvailable;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
