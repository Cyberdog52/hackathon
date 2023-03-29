package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.challenge.bot.service.GameProperties;
import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.GameAction;
import ch.zuehlke.common.shared.action.playing.AttackTurnAction;
import ch.zuehlke.common.shared.event.setup.GameConfigEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Profile("bestbot")
@RequiredArgsConstructor
public class BestBrain implements Brain {

    @NonNull
    private final ApplicationProperties applicationProperties;

    @NonNull
    private final GameProperties gameProperties;

    @Override
    public GameAction decide(Set<GameAction> possibleActions) {
        return null;
    }

    public AttackTurnAction attack() {
        thinkForALongTime();
        Coordinate coordinate = chooseCoordinate();
        return AttackTurnAction.builder()
                .gameId(applicationProperties.getGameId())
                .playerId(applicationProperties.getPlayerId())
                .coordinate(coordinate)
                .build();
    }

    public List<Coordinate> chooseBoatCoordinates(final GameConfigEvent event) {
        return IntStream.range(0, gameProperties.getGameConfig().numberOfBoats())
                .boxed()
                .map(i -> chooseCoordinate())
                .collect(Collectors.toList());
    }

    // failed to place a coordinate so choose a new one
    @Override
    public Coordinate chooseNewBoatCoordinate(final List<Coordinate> coordinates,
                                              final Coordinate failedCoordinate) {
        Coordinate random = chooseCoordinate();
        if (random.equals(failedCoordinate)) {
            return chooseNewBoatCoordinate(coordinates, failedCoordinate);
        }
        return random;
    }

    private Coordinate chooseCoordinate() {
        int x = new Random().nextInt(gameProperties.getGameConfig().mapWidth());
        int y = new Random().nextInt(gameProperties.getGameConfig().mapHeight());
        return Coordinate.builder()
                .x(x)
                .y(y)
                .build();
    }

    private static void thinkForALongTime() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
            // ignore
        }
    }
}
