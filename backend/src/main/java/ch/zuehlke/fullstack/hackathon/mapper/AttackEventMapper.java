package ch.zuehlke.fullstack.hackathon.mapper;

import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.shared.event.playing.AttackEvent;
import ch.zuehlke.common.shared.event.playing.AttackEvent.AttackStatus;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class AttackEventMapper {

  public static AttackEvent mapToAttackEvent(UUID playerId, Coordinate coordinate,
      AttackStatus status, UUID gameId) {
    return AttackEvent.builder()
        .attackingPlayerId(playerId)
        .gameId(gameId)
        .coordinate(coordinate)
        .status(status)
        .build();
  }
}
