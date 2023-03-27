package ch.zuehlke.tablut;

import java.util.Optional;

public record Coordinates(int x, int y) {

    public Optional<Coordinates> left() {
        if (x == 0) {
            return Optional.empty();
        }
        return Optional.of(new Coordinates(x - 1, y));
    }

    public Optional<Coordinates> right(int width) {
        if (x >= width - 1) {
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

    public Optional<Coordinates> down(int height) {
        if (y >= height - 1) {
            return Optional.empty();
        }
        return Optional.of(new Coordinates(x, y + 1));
    }
}
