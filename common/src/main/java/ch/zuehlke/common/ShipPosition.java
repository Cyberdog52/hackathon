package ch.zuehlke.common;

public record ShipPosition(int x, int y) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipPosition that = (ShipPosition) o;
        return x == that.x && y == that.y;
    }

}
