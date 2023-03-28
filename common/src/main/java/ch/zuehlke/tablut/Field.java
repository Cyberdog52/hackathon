package ch.zuehlke.tablut;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record Field(Coordinates coordinates, FieldState state) {
    @Transient
    public boolean isCastle() {
        return coordinates.x() == 4 && coordinates.y() == 4;
    }

    @Transient
    public boolean isAdjacentCastle() {
        Coordinates castle = new Coordinates(4, 4);
        Set<Coordinates> coordinateSet = Set.of(castle.up(), castle.down(), castle.left(), castle.right()).stream()
                .map(Optional::get)
                .collect(Collectors.toSet());

        return coordinateSet.contains(coordinates);
    }

    @Transient
    public boolean isBorder() {
        return coordinates.x() == 0 || coordinates.x() == Board.SIZE -1 ||
                coordinates.y() == 0 || coordinates.y() == Board.SIZE -1;
    }

    @Transient
    public List<Optional<Coordinates>> getNeighbours() {
        return List.of(coordinates.up(), coordinates.down(), coordinates.left(), coordinates.right());
    }
}


