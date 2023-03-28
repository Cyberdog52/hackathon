package ch.zuehlke.common;

import ch.zuehlke.common.cards.Card;
import ch.zuehlke.common.cards.Grouping;

import java.util.Set;

public class RuleEngine {

    public static final int NUMBER_OF_HANDCARDS = 7;

    public static int calculateScore(Set<Card> cards) {
        validateCardValidity(cards);

        return Grouping.group(cards).getNumberOfPoints();
    }

    /**
     * Calculates of the hand is winning and returns the score
     */
    public static boolean isWinning(Set<Card> cards) {
        validateCardValidity(cards);

        return Grouping.group(cards).isWinningHand();
    }

    private static void validateCardValidity(Set<Card> cards) {
        if (cards.size() != NUMBER_OF_HANDCARDS) {
            throw new IllegalArgumentException("Number of cards must be " + NUMBER_OF_HANDCARDS);
        }
    }
}
