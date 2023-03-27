package ch.zuehlke.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Ship {

    ShipType type;
    int x;
    int y;
    Orientation orientation;

    public boolean hits(int x, int y) {
        if (orientation == Orientation.HORIZONTAL) {
            return x >= this.x && x < this.x + type.length && y == this.y;
        } else {
            return y >= this.y && y < this.y + type.length && x == this.x;
        }
    }
}
