package ch.zuehlke.tablut;

import java.beans.Transient;

public record Field(Coordinates coordinates, FieldState state) {
    @Transient
    public boolean isCastle() {
        return coordinates.x() == 4 && coordinates.y() == 4;
    }

    @Transient
    public boolean isBorder() {
        return coordinates.x() == 0 || coordinates.x() == 8 ||
                coordinates.y() == 0 || coordinates.y() == 8;
    }
}


