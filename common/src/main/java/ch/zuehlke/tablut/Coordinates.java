package ch.zuehlke.tablut;

import java.util.Optional;

public record Coordinates(int x, int y) {

    public Optional<Coordinates> left() {
        if (x == 0) {
            return Optional.empty();
        }
        return Optional.of(new Coordinates(x - 1, y));
    }

    public Optional<Coordinates> right() {
        if (x >= Board.SIZE - 1) {
            return Optional.empty();
        }
        return Optional.of(new Coordinates(x + 1, y));
    }

    public Optional<Coordinates> up() {
        if (y == 0) {
            return Optional.empty();
        }
        return Optional.of(new Coordinates(x, y - 1));
    }

    public Optional<Coordinates> down() {
        if (y >= Board.SIZE - 1) {
            return Optional.empty();
        }
        return Optional.of(new Coordinates(x, y + 1));
    }
}
