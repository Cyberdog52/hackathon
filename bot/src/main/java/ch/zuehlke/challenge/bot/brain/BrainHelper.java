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
        Set<Integer> occupiedColumns = new HashSet<>(); // since we simply only place boats UP, we can avoid collisions by not placing new coordinates on a an occupied row

        List<BoatInformation> smallBoatsCoordinates = smallBoats.stream()
                .map(t -> {
                    BoatInformation info = chooseSmallBoatInfo(occupiedCoordinates, occupiedColumns);
                    return info;
                })
                .toList();

        List<BoatInformation> mediumBoatsCoordinates = mediumBoats.stream()
                .map(t -> {
                    BoatInformation info = chooseBoatInfo(occupiedCoordinates, MEDIUM, UP, occupiedColumns);
                    return info;
                })
                .toList();

        List<BoatInformation> largeBoatsCoordinates = largeBoats.stream()
                .map(t -> {
                    BoatInformation info = chooseBoatInfo(occupiedCoordinates, LARGE, UP, occupiedColumns);
                    return info;
                })
                .toList();

        return Stream.of(smallBoatsCoordinates.stream(), mediumBoatsCoordinates.stream(), largeBoatsCoordinates.stream())
                .flatMap(Function.identity())
                .toList();
    }

    private BoatInformation chooseSmallBoatInfo(final Set<Coordinate> occupiedCoordinates, final Set<Integer> occupiedColumns) {
        Coordinate baseCoordinate = chooseSmallBoatCoordinate(occupiedCoordinates, occupiedColumns);
        List<Coordinate> newlyOccupiedCoordinates = List.of(baseCoordinate);
        occupiedCoordinates.addAll(newlyOccupiedCoordinates);
        occupiedColumns.add(baseCoordinate.x());

        return BoatInformation.builder()
                .baseCoordinate(baseCoordinate)
                .boatType(SMALL)
                .boatDirection(UP)
                .build();
    }

    private BoatInformation chooseBoatInfo(final Set<Coordinate> occupiedCoordinates, final BoatType boatType,
                                           final BoatDirection boatDirection, final Set<Integer> occupiedColumns) {
        Coordinate baseCoordinate = chooseMediumBoatCoordinate(occupiedCoordinates, boatDirection, boatType, occupiedColumns);
        List<Coordinate> newlyOccupiedCoordinates = BoatPlacementHelper.determineBoatCoordinates(MEDIUM, UP, baseCoordinate);
        occupiedCoordinates.addAll(newlyOccupiedCoordinates);
        occupiedColumns.add(baseCoordinate.x());

        return BoatInformation.builder()
                .baseCoordinate(baseCoordinate)
                .boatType(boatType)
                .boatDirection(boatDirection)
                .build();
    }

    private Coordinate chooseSmallBoatCoordinate(final Set<Coordinate> occupiedCoordinates,
                                                 final Set<Integer> occupiedRows) {
        Coordinate coordinate = chooseCoordinate();
        if (occupiedCoordinates.contains(coordinate) || occupiedRows.contains(coordinate.x())) {
            return chooseSmallBoatCoordinate(occupiedCoordinates, occupiedRows);
        }
        return coordinate;
    }

    private Coordinate chooseMediumBoatCoordinate(final Set<Coordinate> occupiedCoordinates,
                                                  final BoatDirection boatDirection, final BoatType boatType,
                                                  final Set<Integer> occupiedColumns) {
        Coordinate coordinate = chooseCoordinate(boatDirection, boatType);
        if (occupiedCoordinates.contains(coordinate) || occupiedColumns.contains(coordinate.x())) {
            return chooseMediumBoatCoordinate(occupiedCoordinates, boatDirection, boatType, occupiedColumns);
        }
        return coordinate;
    }

    private Coordinate chooseCoordinate(final BoatDirection boatDirection, final BoatType boatType) {
        int x = 0;
        int y = 0;
        if (boatDirection.equals(UP)) {
            int diff = gameProperties.getGameConfig().mapWidth() - boatType.getBoatSize();
            y = diff == 0 ? 0 : new Random().nextInt(0, diff);
            x = new Random().nextInt(gameProperties.getGameConfig().mapHeight());
        }
        if (boatDirection.equals(DOWN)) {
            int diff = gameProperties.getGameConfig().mapWidth() - boatType.getBoatSize();
            y = diff == 0 ? boatType.getBoatSize() : new Random().nextInt(boatType.getBoatSize(), gameProperties.getGameConfig().mapWidth());
            x = new Random().nextInt(gameProperties.getGameConfig().mapHeight());
        }
        if (boatDirection.equals(LEFT)) {
            int diff = gameProperties.getGameConfig().mapWidth() - boatType.getBoatSize();
            x = diff == 0 ? boatType.getBoatSize() : new Random().nextInt(boatType.getBoatSize(), gameProperties.getGameConfig().mapHeight());
            y = new Random().nextInt(gameProperties.getGameConfig().mapWidth());
        }
        if (boatDirection.equals(RIGHT)) {
            int diff = gameProperties.getGameConfig().mapWidth() - boatType.getBoatSize();
            x = diff == 0 ? 0 : new Random().nextInt(gameProperties.getGameConfig().mapHeight() - boatType.getBoatSize());
            y = new Random().nextInt(gameProperties.getGameConfig().mapWidth());
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
