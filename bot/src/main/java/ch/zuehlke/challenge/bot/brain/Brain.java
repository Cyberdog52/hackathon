package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.common.Board;
import ch.zuehlke.common.gameplay.ShootRequest;

import java.util.Set;

public interface Brain {

    Board createGame(String gameId);

    ShootRequest shootRequest(String gameId);

}
