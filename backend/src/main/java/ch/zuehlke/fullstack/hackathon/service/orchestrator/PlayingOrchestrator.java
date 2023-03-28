package ch.zuehlke.fullstack.hackathon.service.orchestrator;

import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.shared.event.playing.AttackEvent;
import ch.zuehlke.common.shared.event.playing.AttackEvent.AttackStatus;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.GameMap;
import ch.zuehlke.fullstack.hackathon.model.ThunderShipsPlayer;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import ch.zuehlke.fullstack.hackathon.service.GameService;
import ch.zuehlke.fullstack.hackathon.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayingOrchestrator {

  private final NotificationService notificationService;
  private final GameService gameService;


  public Game attack(UUID gameId, UUID playerId, Coordinate coordinate, GameState currentState,
      UUID firstPlayerId) {
    Game game = gameService.get(gameId);
    if (isAttackInvalid(firstPlayerId, currentState, playerId)) {
      AttackEvent attackEvent = AttackEvent.builder()
          .gameId(gameId)
          .attackingPlayerId(playerId)
          .coordinate(coordinate)
          .status(AttackStatus.IGNORED)
          .build();
      notificationService.notifySpectatorPlayerAttacked(attackEvent);
      notificationService.notifyPlayerAttacked(attackEvent);
      return game;
    }

    GameMap targetMap = game.players().stream()
        .filter(player -> !player.id().equals(playerId))
        .map(ThunderShipsPlayer::gameMap)
        .findAny()
        .orElseThrow(() -> new IllegalStateException("Player %s has no enemy".formatted(playerId)));

    boolean anyBoatHit = targetMap.boats().stream()
        .anyMatch(boat -> boat.isHit(coordinate));

    AttackEvent attackEvent;

    if (anyBoatHit) {
      attackEvent = AttackEvent.builder()
          .gameId(gameId)
          .attackingPlayerId(playerId)
          .coordinate(coordinate)
          .status(AttackStatus.HIT)
          .build();
    } else {
      attackEvent = AttackEvent.builder()
          .gameId(gameId)
          .attackingPlayerId(playerId)
          .coordinate(coordinate)
          .status(AttackStatus.MISS)
          .build();
    }

    notificationService.notifySpectatorPlayerAttacked(attackEvent);
    notificationService.notifyPlayerAttacked(attackEvent);

    return game;
  }

  private boolean isAttackInvalid(UUID firstPlayerId, GameState currentState,
      UUID attackingPlayerId) {
    boolean player1AttackingOutOfOrder =
        firstPlayerId == attackingPlayerId && currentState.equals(GameState.PLAYING_PLAYER_2);
    boolean player2AttackingOutOfORder =
        firstPlayerId != attackingPlayerId && currentState.equals(GameState.PLAYING_PLAYER_1);

    return player1AttackingOutOfOrder || player2AttackingOutOfORder;
  }
}
