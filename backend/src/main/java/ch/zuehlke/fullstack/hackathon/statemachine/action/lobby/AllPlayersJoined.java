package ch.zuehlke.fullstack.hackathon.statemachine.action.lobby;

import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.Lobby;
import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import ch.zuehlke.fullstack.hackathon.service.orchestrator.SetupOrchestrator;
import ch.zuehlke.fullstack.hackathon.statemachine.Variable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AllPlayersJoined {

    @NonNull
    private final SetupOrchestrator setupOrchestrator;

    public Action<GameState, GameEvent> allPlayersJoined() {
        return context -> {
            var stateMachine = context.getStateMachine();
            Lobby lobby = (Lobby) stateMachine.getExtendedState().getVariables().get(Variable.LOBBY_ID);

            Game game = setupOrchestrator.initialiseGame(lobby);
            stateMachine.getExtendedState().getVariables().put(Variable.GAME_ID, game);
        };
    }

}
