package ch.zuehlke.common;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Ship {

    @NonNull
    ShipType type;

    Set<ShipPosition> positions = new HashSet<>();
    Set<ShipPosition> destroyed = new HashSet<>();

    int x;

    int y;

    @NonNull
    Orientation orientation;

    public Ship(ShipType type, int x, int y, Orientation orientation) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.orientation = orientation;

        for (int i = 0; i < type.length; i++) {
            if (orientation.equals(Orientation.HORIZONTAL)) {
                positions.add(new ShipPosition(x + i, y));
            } else {
                positions.add(new ShipPosition(x, y + i));
            }
        }
    }

    public boolean isDestroyed() {
        return destroyed.size() == type.length;
    }

    public boolean hits(int x, int y) {
        if (orientation == Orientation.HORIZONTAL) {
            if(x >= this.x && x < this.x + type.length && y == this.y) {
                destroyed.add(new ShipPosition(x, y));
                return true;
            }
        } else {
            if(y >= this.y && y < this.y + type.length && x == this.x) {
                destroyed.add(new ShipPosition(x, y));
                return true;
            }
        }
        return false;
    }

    public int shortestDistanceToShot(int xShootPosition, int yShootPosition) {
        int shortestDistance = distanceToShot(x, y, xShootPosition, yShootPosition);
        for (int i = 1; i < type.length; i++) {
            int tmp;
            if (orientation.equals(Orientation.HORIZONTAL)) {
                tmp = distanceToShot(x + i, y, xShootPosition, yShootPosition);
            } else {
                tmp = distanceToShot(x, y + 1, xShootPosition, yShootPosition);
            }
            if(shortestDistance > tmp)
                shortestDistance = tmp;
        }
        return shortestDistance;
    }

    private int distanceToShot(int xShip, int yShip, int xShootPosition, int yShootPosition) {
        int distanceX = distanceBetween(xShip, xShootPosition);
        int distanceY = distanceBetween(yShip, yShootPosition);
        return Math.max(distanceX, distanceY);
    }

    private int distanceBetween(int shipPos, int shootPos) {
        if (shipPos >= shootPos)
            return shipPos - shootPos;
        return shootPos - shipPos;
    }
}
