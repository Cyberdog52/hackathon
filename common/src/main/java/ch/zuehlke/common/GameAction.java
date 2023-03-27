package ch.zuehlke.common;

import ch.zuehlke.tablut.Coordinates;

public record GameAction(Coordinates from, Coordinates to) {
    public static GameAction create(int fromX, int fromY, int toX, int toY) {
        return new GameAction(new Coordinates(fromX, fromY), new Coordinates(toX, toY));
    }
}
