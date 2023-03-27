package ch.zuehlke.common;

public enum ShipType {
    AIRCRAFT_CARRIER(5), BATTLESHIP(4), SUBMARINE(3), CRUISER(3), DESTROYER(2);

    public final int length;

    ShipType(int size) {
        this.length = size;
    }
}
