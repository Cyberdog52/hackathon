package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public final class Boat {

  private final Coordinate coordinate;
  private boolean destroyed;

  public boolean isHit(Coordinate coordinate) {
    if (this.coordinate.equals(coordinate)) {
      destroyed = true;
      return true;
    }
    return false;
  }
}
