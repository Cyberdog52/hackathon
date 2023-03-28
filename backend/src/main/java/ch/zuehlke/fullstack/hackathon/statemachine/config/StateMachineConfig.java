package ch.zuehlke.fullstack.hackathon.statemachine.config;

import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import ch.zuehlke.fullstack.hackathon.statemachine.action.GameAction;
import ch.zuehlke.fullstack.hackathon.statemachine.action.lobby.AllPlayersJoined;
import ch.zuehlke.fullstack.hackathon.statemachine.action.lobby.PlayerJoinAction;
import ch.zuehlke.fullstack.hackathon.statemachine.action.setup.PlaceBoat;
import ch.zuehlke.fullstack.hackathon.statemachine.guard.GameGuard;
import ch.zuehlke.fullstack.hackathon.statemachine.listener.GameListener;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.Set;

import static ch.zuehlke.fullstack.hackathon.model.game.GameEvent.*;
import static ch.zuehlke.fullstack.hackathon.model.game.state.GameState.*;
import static ch.zuehlke.fullstack.hackathon.statemachine.action.PlayerJoinAction.playerJoinAction;

@Configuration
@EnableStateMachine
@RequiredArgsConstructor
@Slf4j
public class StateMachineConfig
        extends EnumStateMachineConfigurerAdapter<GameState, GameEvent> {

    @NonNull
    private final GameAction action;

    @NonNull
    private final PlayerJoinAction playerJoinAction;

    @NonNull
    private final AllPlayersJoined allPlayersJoined;

    @NonNull
    private final PlaceBoat placeBoat;

    @NonNull
    private final GameListener listener;

    @NonNull
    private final GameGuard guard;

    @Override
    public void configure(StateMachineConfigurationConfigurer<GameState, GameEvent> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener);
    }

    @Override
    public void configure(StateMachineStateConfigurer<GameState, GameEvent> gameState)
            throws Exception {
        gameState
                .withStates()
                .initial(LOBBY)
                .states(Set.of(LOBBY, SETUP, PLAYING, END))
                .and()
                .withStates()
                .parent(PLAYING)
                .initial(PLAYING_PLAYER_1)
                .states(Set.of(PLAYING_PLAYER_1, PLAYING_PLAYER_2))
                .end(END);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<GameState, GameEvent> transitions)
            throws Exception {
        transitions
                // LOBBY
                .withInternal()
                .source(LOBBY).event(PLAYER_JOINED).action(playerJoinAction.playerJoin()).guard(guard).and()
                .withExternal()
                .source(LOBBY).target(SETUP).event(ALL_PLAYERS_JOINED).action(allPlayersJoined.allPlayersJoined()).and()

                // SETUP
                .withInternal()
                .source(SETUP).event(PLACE_BOAT).action(placeBoat.placeBoat()).guard(guard).and()
                .withExternal()
                .source(SETUP).target(PLAYING).event(ALL_BOATS_PLACED).action(placeBoat.allBoatsPlaced()).guard(guard).and()

                // PLAYING
                .withExternal()
                .source(PLAYING).target(END).event(ALL_BOATS_DESTROYED).action(action).and()
                .withExternal()
                .source(PLAYING_PLAYER_1).target(PLAYING_PLAYER_2).event(ATTACK).and()
                .withExternal()
                .source(PLAYING_PLAYER_2).target(PLAYING_PLAYER_1).event(ATTACK).and()
                .withExternal()
                .source(PLAYING_PLAYER_1).target(END).event(ALL_BOATS_DESTROYED).and()
                .withExternal()
                .source(PLAYING_PLAYER_2).target(END).event(ALL_BOATS_DESTROYED);
    }
}
