package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.common.Player;
import ch.zuehlke.common.gameplay.PlaceShipsRequest;
import ch.zuehlke.common.gameplay.ShootRequest;

public interface Brain {

    int THINK_TIME = 500;

    PlaceShipsRequest createGame(String gameId, Player player);

    ShootRequest shootRequest(String gameId, Player player);

}
