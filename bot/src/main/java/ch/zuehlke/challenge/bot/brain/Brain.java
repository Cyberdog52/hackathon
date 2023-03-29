package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.common.Board;
import ch.zuehlke.common.Player;
import ch.zuehlke.common.gameplay.PlaceShipsRequest;
import ch.zuehlke.common.gameplay.ShootRequest;

import java.util.Set;

public interface Brain {

    PlaceShipsRequest createGame(String gameId, Player player);

    ShootRequest shootRequest(String gameId);

}
