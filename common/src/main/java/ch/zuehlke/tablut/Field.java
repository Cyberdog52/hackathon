package ch.zuehlke.tablut;

import java.beans.Transient;

public record Field(Coordinates coordinates, FieldState state) {
    @Transient
    public boolean isCastle() {
        return coordinates.x() == 4 && coordinates.y() == 4;
    }
}


