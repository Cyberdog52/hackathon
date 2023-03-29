package ch.zuehlke.fullstack.hackathon.statemachine.action.end;

import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import ch.zuehlke.fullstack.hackathon.service.orchestrator.GameEndOrchestrator;
import ch.zuehlke.fullstack.hackathon.statemachine.Variable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EndGame {

  @NonNull
  private final GameEndOrchestrator gameEndOrchestrator;

  public Action<GameState, GameEvent> endGame() {
    return context -> {
      var stateMachine = context.getStateMachine();

      Game storedGame = (Game) stateMachine.getExtendedState().getVariables().get(Variable.GAME_ID);

      gameEndOrchestrator.endGame(storedGame);
    };
  }
}
