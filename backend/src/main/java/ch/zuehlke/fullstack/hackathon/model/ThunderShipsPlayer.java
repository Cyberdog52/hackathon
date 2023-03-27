package ch.zuehlke.fullstack.hackathon.model;

import java.util.UUID;
import java.util.function.Predicate;

public record ThunderShipsPlayer(UUID id, GameMap gameMap) {
  public boolean hasAnyBoatsLeft() {
    return gameMap.boats().stream().anyMatch(Predicate.not(Boat::isDestroyed));
  }
}
