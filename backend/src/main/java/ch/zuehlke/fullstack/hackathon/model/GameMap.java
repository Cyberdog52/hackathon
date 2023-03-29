package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.Coordinate;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record GameMap(
        Set<Boat> boats,
        int height,
        int width) {

    public boolean placeBoat(Boat boat) {
        if (checkBoatPlacing(boat)) {
            return boats.add(boat);
        }
        return false;
    }

    private boolean checkBoatPlacing(Boat boat) {
        var squareFree = boats.stream()
                .map(Boat::getCoordinates)
                .allMatch(coordinates -> noOverlaps(coordinates, boat.getCoordinates()));

        if (!squareFree) {
            return false;
        }

        return boat.getCoordinates().stream()
                .map(coordinate -> isInBounds(coordinate, width, height))
                .reduce((a, b) -> a && b)
                .orElse(false);
    }

    private boolean noOverlaps(List<Coordinate> coordinates, List<Coordinate> otherCoordinates) {
        return coordinates.stream()
                .allMatch(coordinate -> otherCoordinates.stream()
                        .noneMatch(coordinate1 -> overlaps(coordinate1, coordinate)));
    }

    private boolean overlaps(Coordinate current, Coordinate other) {
        return current.x() == other.x() && current.y() == other.y();
    }

    public static boolean isInBounds(Coordinate coordinate, int width, int height) {
        var horizontallyInBound = coordinate.x() < width && coordinate.x() >= 0;
        var verticallyInBound = coordinate.y() < height && coordinate.y() >= 0;
        return horizontallyInBound && verticallyInBound;
    }
}
