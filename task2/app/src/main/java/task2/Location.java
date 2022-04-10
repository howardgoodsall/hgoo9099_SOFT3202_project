package task2;

public class Location {
    private String location;
    private int price;
    private String system;

    public Location(String location, int price, String system) {
        this.location = location;
        this.price = price;
        this.system = system;
    }

    public String getLocation() {
        return this.location;
    }

    public int getPrice() {
        return this.price;
    }

    public String getSystem() {
        return this.system;
    }
}
