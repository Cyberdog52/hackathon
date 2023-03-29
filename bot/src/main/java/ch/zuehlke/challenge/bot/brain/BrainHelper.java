package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.challenge.bot.model.BoatInformation;
import ch.zuehlke.challenge.bot.service.GameProperties;
import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.helperfunction.BoatPlacementHelper;
import ch.zuehlke.common.shared.action.setup.BoatDirection;
import ch.zuehlke.common.shared.action.setup.BoatType;
import ch.zuehlke.common.shared.event.setup.GameConfigEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static ch.zuehlke.common.shared.action.setup.BoatDirection.*;
import static ch.zuehlke.common.shared.action.setup.BoatType.*;

@Service
@RequiredArgsConstructor
public class BrainHelper {

    @NonNull
    private final GameProperties gameProperties;

    public List<BoatInformation> chooseBoatCoordinates(final GameConfigEvent event) {
        List<BoatType> largeBoats = event.boatRequirements().boatTypes().stream().filter(t -> t.equals(LARGE)).toList();
        List<BoatType> mediumBoats = event.boatRequirements().boatTypes().stream().filter(t -> t.equals(MEDIUM)).toList();
        List<BoatType> smallBoats = event.boatRequirements().boatTypes().stream().filter(t -> t.equals(SMALL)).toList();

        // not implementing checking the boat bounds
        Set<Coordinate> occupiedCoordinates = new HashSet<>();

        List<BoatInformation> smallBoatsCoordinates = smallBoats.stream()
                .map(t -> {
                    BoatInformation info = chooseSmallBoatInfo(occupiedCoordinates);
                    return info;
                })
                .toList();

        List<BoatInformation> mediumBoatsCoordinates = mediumBoats.stream()
                .map(t -> {
                    BoatInformation info = chooseBoatInfo(occupiedCoordinates, MEDIUM, UP);
                    return info;
                })
                .toList();

        List<BoatInformation> largeBoatsCoordinates = largeBoats.stream()
                .map(t -> {
                    BoatInformation info = chooseBoatInfo(occupiedCoordinates, LARGE, UP);
                    return info;
                })
                .toList();

        return Stream.of(smallBoatsCoordinates.stream(), mediumBoatsCoordinates.stream(), largeBoatsCoordinates.stream())
                .flatMap(Function.identity())
                .toList();
    }

    private BoatInformation chooseSmallBoatInfo(final Set<Coordinate> occupiedCoordinates) {
        Coordinate baseCoordinate = chooseSmallBoatCoordinate(occupiedCoordinates);
        List<Coordinate> newlyOccupiedCoordinates = List.of(baseCoordinate);
        occupiedCoordinates.addAll(newlyOccupiedCoordinates);

        return BoatInformation.builder()
                .baseCoordinate(baseCoordinate)
                .boatType(SMALL)
                .boatDirection(UP)
                .build();
    }

    private BoatInformation chooseBoatInfo(final Set<Coordinate> occupiedCoordinates, final BoatType boatType,
                                           final BoatDirection boatDirection) {
        Coordinate baseCoordinate = chooseMediumBoatCoordinate(occupiedCoordinates, boatDirection, boatType);
        List<Coordinate> newlyOccupiedCoordinates = BoatPlacementHelper.determineBoatCoordinates(MEDIUM, UP, baseCoordinate);
        occupiedCoordinates.addAll(newlyOccupiedCoordinates);

        return BoatInformation.builder()
                .baseCoordinate(baseCoordinate)
                .boatType(boatType)
                .boatDirection(boatDirection)
                .build();
    }

    private Coordinate chooseSmallBoatCoordinate(final Set<Coordinate> occupiedCoordinates) {
        Coordinate coordinate = chooseCoordinate();
        if (occupiedCoordinates.contains(coordinate)) {
            return chooseSmallBoatCoordinate(occupiedCoordinates);
        }
        return coordinate;
    }

    private Coordinate chooseMediumBoatCoordinate(final Set<Coordinate> occupiedCoordinates,
                                                  final BoatDirection boatDirection, final BoatType boatType) {
        Coordinate coordinate = chooseCoordinate(boatDirection, boatType);
        if (occupiedCoordinates.contains(coordinate)) {
            return chooseMediumBoatCoordinate(occupiedCoordinates, boatDirection, boatType);
        }
        return coordinate;
    }

    private Coordinate chooseCoordinate(final BoatDirection boatDirection, final BoatType boatType) {
        int x = 0;
        int y = 0;
        if (boatDirection.equals(UP)) {
            y = new Random().nextInt(0, gameProperties.getGameConfig().mapWidth() - boatType.getBoatSize());
            x = new Random().nextInt(gameProperties.getGameConfig().mapHeight());
        }
        if (boatDirection.equals(DOWN)) {
            y = new Random().nextInt(boatType.getBoatSize(), gameProperties.getGameConfig().mapWidth());
            x = new Random().nextInt(gameProperties.getGameConfig().mapHeight());
        }
        if (boatDirection.equals(LEFT)) {
            y = new Random().nextInt(gameProperties.getGameConfig().mapWidth());
            x = new Random().nextInt(boatType.getBoatSize(), gameProperties.getGameConfig().mapHeight());
        }
        if (boatDirection.equals(RIGHT)) {
            y = new Random().nextInt(gameProperties.getGameConfig().mapWidth());
            x = new Random().nextInt(gameProperties.getGameConfig().mapHeight() - boatType.getBoatSize());
        }
        return Coordinate.builder()
                .x(x)
                .y(y)
                .build();
    }

    private Coordinate chooseCoordinate() {
        int x = new Random().nextInt(gameProperties.getGameConfig().mapWidth());
        int y = new Random().nextInt(gameProperties.getGameConfig().mapHeight());
        return Coordinate.builder()
                .x(x)
                .y(y)
                .build();
    }

}
