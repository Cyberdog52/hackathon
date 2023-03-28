package ch.zuehlke.common;

import ch.zuehlke.tablut.Coordinates;

public record GameAction(Coordinates from, Coordinates to) {
    public GameAction(int fromX, int fromY, int toX, int toY) {
        this(new Coordinates(fromX, fromY), new Coordinates(toX, toY));
    }
}
