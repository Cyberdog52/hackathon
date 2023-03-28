package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.common.GameAction;
import ch.zuehlke.tablut.Board;

import java.util.Set;

public interface Brain {

    GameAction decide(boolean attacker, Board board, Set<GameAction> possibleActions);
}
