package ch.zuehlke.fullstack.hackathon.service.orchestrator;

import ch.zuehlke.common.shared.event.GameEndEvent;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.ThunderShipsPlayer;
import ch.zuehlke.fullstack.hackathon.service.GameService;
import ch.zuehlke.fullstack.hackathon.service.NotificationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class GameEndOrchestrator {

  @NonNull
  private final NotificationService notificationService;
  @NonNull
  private final GameService gameService;

  public void endGame(Game storedGame) {
    Optional<ThunderShipsPlayer> winner = storedGame.players().stream()
        .filter(Predicate.not(ThunderShipsPlayer::hasAnyBoatsLeft))
        .findFirst();
    if (winner.isEmpty()) {
      throw new IllegalStateException("All Players still have at leas one boat still left!");
    }
    GameEndEvent gameEndEvent = GameEndEvent.builder()
        .gameId(storedGame.gameId())
        .winnerId(winner.get().id())
        .build();

    notificationService.notifySpectatorGameEnded(gameEndEvent);
    notificationService.notifyGameEnded(gameEndEvent);
  }
}
