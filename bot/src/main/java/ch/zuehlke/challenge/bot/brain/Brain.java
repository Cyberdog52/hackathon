package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.common.GameAction;

import java.util.Set;

public interface Brain {

    GameAction decide(Set<GameAction> possibleActions);

    String name();

    String icon();
}
