package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.challenge.bot.model.BoatInformation;
import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.GameAction;
import ch.zuehlke.common.shared.action.playing.AttackTurnAction;
import ch.zuehlke.common.shared.event.setup.GameConfigEvent;

import java.util.List;
import java.util.Set;

public interface Brain {

    GameAction decide(Set<GameAction> possibleActions);

    AttackTurnAction attack();

    List<BoatInformation> chooseBoatCoordinates(GameConfigEvent event);

    Coordinate chooseNewBoatCoordinate(List<Coordinate> coordinates, Coordinate failedCoordinate);

}
