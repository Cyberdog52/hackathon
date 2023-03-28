package ch.zuehlke.fullstack.hackathon;

import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.statemachine.StateMachine;
import reactor.core.publisher.Mono;

import static ch.zuehlke.fullstack.hackathon.model.game.GameEvent.*;

@EnableScheduling
@SpringBootApplication
public class HackathonApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HackathonApplication.class, args);
    }

    @Autowired
    private StateMachine<GameState, GameEvent> stateMachine;

    @Override
    public void run(String... args) {
        stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(PLAYER_JOINED).build())).blockLast();
        stateMachine.sendEvent(PLAYER_JOINED);

        stateMachine.sendEvent(PLACE_BOAT);

        stateMachine.sendEvent(ATTACK);
        stateMachine.sendEvent(ATTACK);
        stateMachine.sendEvent(ATTACK);
        stateMachine.sendEvent(ATTACK);
    }
}
