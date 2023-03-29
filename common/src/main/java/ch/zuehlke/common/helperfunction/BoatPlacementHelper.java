package ch.zuehlke.common.helperfunction;

import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.shared.action.setup.BoatDirection;
import ch.zuehlke.common.shared.action.setup.BoatType;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class BoatPlacementHelper {

    public static List<Coordinate> determineBoatCoordinates(final BoatType boatType, final BoatDirection boatDirection,
                                                            final Coordinate coordinate) {
        return IntStream.range(0, boatType.getBoatSize())
                .boxed()
                .map(delta -> determineNextCoordinate(boatDirection, coordinate, delta))
                .collect(Collectors.toList());
    }

    public static Coordinate determineNextCoordinate(final BoatDirection boatDirection, final Coordinate coordinate,
                                                     final int delta) {
        return switch (boatDirection) {
            case UP -> coordinate.toBuilder().y(coordinate.y() + delta).build();
            case DOWN -> coordinate.toBuilder().y(coordinate.y() - delta).build();
            case RIGHT -> coordinate.toBuilder().x(coordinate.x() + delta).build();
            case LEFT -> coordinate.toBuilder().x(coordinate.x() - delta).build();
        };
    }

}
