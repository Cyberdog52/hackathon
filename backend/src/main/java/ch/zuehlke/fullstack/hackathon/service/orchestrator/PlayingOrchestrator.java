package ch.zuehlke.fullstack.hackathon.service.orchestrator;

import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.GamePlayingAction;
import ch.zuehlke.common.shared.event.playing.AttackEvent;
import ch.zuehlke.common.shared.event.playing.AttackEvent.AttackStatus;
import ch.zuehlke.common.shared.event.playing.TakeTurnEvent;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.ThunderShipsPlayer;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import ch.zuehlke.fullstack.hackathon.service.GameService;
import ch.zuehlke.fullstack.hackathon.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    ThunderShipsPlayer otherPlayer = game.players().stream()
        .filter(player -> !player.id().equals(playerId))
        .findFirst()
        .orElseThrow(
            () -> new IllegalStateException("Player %s has no enemy.".formatted(playerId)));

    boolean anyBoatHit = otherPlayer.gameMap().boats().stream()
        .anyMatch(boat -> boat.isHit(coordinate));

    AttackEvent attackEvent = createAttackEvent(anyBoatHit, gameId, playerId, coordinate);

    TakeTurnEvent takeTurnEvent = TakeTurnEvent.builder()
        .actions(List.of(GamePlayingAction.ATTACK))
        .playerId(otherPlayer.id())
        .build();

    notificationService.notifySpectatorPlayerAttacked(attackEvent);
    notificationService.notifyPlayerAttacked(attackEvent);
    notificationService.notifyPlayerTakeTurn(takeTurnEvent);
    notificationService.notifySpectatorPlayerTurn(takeTurnEvent);

    return game;
  }

  private static AttackEvent createAttackEvent(boolean anyBoatHit, UUID gameId, UUID playerId, Coordinate coordinate) {
    AttackEvent attackEvent;
    attackEvent = AttackEvent.builder()
        .gameId(gameId)
        .attackingPlayerId(playerId)
        .coordinate(coordinate)
        .status(anyBoatHit ? AttackStatus.HIT : AttackStatus.MISS)
        .build();
    return attackEvent;
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
