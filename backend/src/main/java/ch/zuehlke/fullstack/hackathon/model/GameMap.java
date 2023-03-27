package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.Coordinate;

import java.util.List;

public record GameMap(List<Boat> boats, int height, int width) {

  public boolean placeBoat(Boat boat) {
    if (checkBoatPlacing(boat)) {
      return boats.add(boat);
    }
    return false;
  }

  private boolean checkBoatPlacing(Boat boat) {
    var squareOccupied = boats.stream()
        .map(Boat::getCoordinate)
        .anyMatch(coordinate -> overlaps(coordinate, boat.getCoordinate()));
    if (squareOccupied) {
      return false;
    }

    return isInBounds(boat.getCoordinate());
  }

  private boolean overlaps(Coordinate current, Coordinate other) {
    return current.x() == other.x() && current.y() == other.y();
  }

  private boolean isInBounds(Coordinate coordinate) {
    var horizontallyInBound = coordinate.x() < width && coordinate.x() >= 0;
    var verticallyInBound = coordinate.y() < height && coordinate.y() >= 0;
    return horizontallyInBound && verticallyInBound;
  }
}
