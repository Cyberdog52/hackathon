package ch.zuehlke.tablut;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
