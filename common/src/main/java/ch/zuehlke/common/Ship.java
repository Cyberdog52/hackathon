package ch.zuehlke.common;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Ship {

    @NonNull
    ShipType type;

    int x;

    int y;

    @NonNull
    Orientation orientation;

    int hits = 0;

    public Ship(ShipType type, int x, int y, Orientation orientation) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public boolean hits(int x, int y) {
        if (orientation == Orientation.HORIZONTAL) {
            return x >= this.x && x < this.x + type.length && y == this.y;
        } else {
            return y >= this.y && y < this.y + type.length && x == this.x;
        }
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
