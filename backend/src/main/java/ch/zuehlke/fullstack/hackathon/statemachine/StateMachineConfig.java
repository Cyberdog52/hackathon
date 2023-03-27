package ch.zuehlke.fullstack.hackathon.statemachine;

import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import ch.zuehlke.fullstack.hackathon.statemachine.action.GameAction;
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

@Configuration
@EnableStateMachine
@RequiredArgsConstructor
@Slf4j
public class StateMachineConfig
        extends EnumStateMachineConfigurerAdapter<GameState, GameEvent> {

    @NonNull
    private final GameAction action;

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
                .withExternal()
                .source(LOBBY).target(SETUP).event(PLAYER_JOINED).action(action).guard(guard).and()

                // SETUP
                .withExternal()
                .source(SETUP).target(PLAYING).event(PLACE_BOAT).guard(guard).and()

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
