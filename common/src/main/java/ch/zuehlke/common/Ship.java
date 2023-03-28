package ch.zuehlke.common;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
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

    public boolean hits(int x, int y) {
        if (orientation == Orientation.HORIZONTAL) {
            return x >= this.x && x < this.x + type.length && y == this.y;
        } else {
            return y >= this.y && y < this.y + type.length && x == this.x;
        }
    }
}
