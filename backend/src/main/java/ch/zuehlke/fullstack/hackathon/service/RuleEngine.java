package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.Card;
import ch.zuehlke.fullstack.hackathon.model.Grouping;

import java.util.Set;

public class RuleEngine {

    public static final int NUMBER_OF_HANDCARDS = 7;

    public static int calculateScore(Set<Card> cards) {
        if (cards.size() != NUMBER_OF_HANDCARDS) {
            throw new IllegalArgumentException("Number of cards must be " + NUMBER_OF_HANDCARDS);
        }

         return Grouping.group(cards).getNumberOfPoints();
    }

    /**
     * Calculates of the hand is winning and returns the score
     */
    public static boolean isWinning(Set<Card> cards) {
if (cards.size() != NUMBER_OF_HANDCARDS) {
            throw new IllegalArgumentException("Number of cards must be " + NUMBER_OF_HANDCARDS);
        }

        return Grouping.group(cards).isWinningHand();
    }
}
