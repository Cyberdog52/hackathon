package ch.zuehlke.fullstack.hackathon.model;

import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;
import java.util.function.Predicate;

@Builder
public record ThunderShipsPlayer(
        @NonNull UUID id,
        @NonNull GameMap gameMap) {
  public boolean hasAnyBoatsLeft() {
    return gameMap.boats().stream().anyMatch(Predicate.not(Boat::isDestroyed));
  }
}
