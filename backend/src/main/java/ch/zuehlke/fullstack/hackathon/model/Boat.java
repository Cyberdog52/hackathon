package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.helperfunction.BoatPlacementHelper;
import ch.zuehlke.common.shared.action.setup.BoatDirection;
import ch.zuehlke.common.shared.action.setup.BoatType;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public final class Boat {
    @NonNull
    private final UUID boatId;
    private final List<Coordinate> coordinates;
    @NonNull
    private final Set<Coordinate> hitCoordinates;
    private boolean destroyed;

    public boolean isHit(Coordinate coordinate) {
        if (this.coordinates.contains(coordinate)) {
            this.hitCoordinates.add(coordinate);
            if (this.hitCoordinates.size() == this.coordinates.size()) {
                this.destroyed = true;
            }
            return true;
        }
        return false;
    }

    public static boolean validBoatCoordinates(List<Coordinate> coordinates, final int mapHeight, final int mapWidth) {
        return coordinates.stream().allMatch(coordinate -> GameMap.isInBounds(coordinate, mapHeight, mapWidth));
    }

    public static List<Coordinate> determineBoatCoordinates(final BoatType boatType, final BoatDirection boatDirection,
                                                            final Coordinate coordinate) {
        return BoatPlacementHelper.determineBoatCoordinates(boatType, boatDirection, coordinate);
    }

}
