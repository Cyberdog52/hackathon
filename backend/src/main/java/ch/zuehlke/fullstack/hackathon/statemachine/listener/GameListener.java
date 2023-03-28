package ch.zuehlke.fullstack.hackathon.statemachine.listener;

import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import ch.zuehlke.fullstack.hackathon.service.orchestrator.SetupOrchestrator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import static ch.zuehlke.fullstack.hackathon.model.game.state.GameState.LOBBY;
import static ch.zuehlke.fullstack.hackathon.model.game.state.GameState.SETUP;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameListener implements StateMachineListener<GameState, GameEvent> {

    @NonNull
    private final SetupOrchestrator setupOrchestrator;

    @Override
    public void stateChanged(State<GameState, GameEvent> from, State<GameState, GameEvent> to) {
        log.info("State change to " + to.getId());
    }

    @Override
    public void stateEntered(State<GameState, GameEvent> state) {
        log.info("State entered " + state.getId());
    }

    @Override
    public void stateExited(State<GameState, GameEvent> state) {
        log.info("State exited " + state.getId());
    }

    @Override
    public void eventNotAccepted(Message<GameEvent> event) {

    }

    @Override
    public void transition(Transition<GameState, GameEvent> transition) {
        log.info("State transition " + transition);
    }

    @Override
    public void transitionStarted(Transition<GameState, GameEvent> transition) {

    }

    @Override
    public void transitionEnded(Transition<GameState, GameEvent> transition) {
        if (transition.getSource().getId().equals(LOBBY) && transition.getTarget().getId().equals(SETUP)) {
            setupOrchestrator.initialiseGame();
        }
    }

    @Override
    public void stateMachineStarted(StateMachine<GameState, GameEvent> stateMachine) {
        log.info("State machine started" + stateMachine);
    }

    @Override
    public void stateMachineStopped(StateMachine<GameState, GameEvent> stateMachine) {

    }

    @Override
    public void stateMachineError(StateMachine<GameState, GameEvent> stateMachine, Exception exception) {

    }

    @Override
    public void extendedStateChanged(Object key, Object value) {

    }

    @Override
    public void stateContext(StateContext<GameState, GameEvent> stateContext) {

    }
}
