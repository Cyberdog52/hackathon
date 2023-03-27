package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.Card;
import ch.zuehlke.fullstack.hackathon.model.CardSetComparator;

import java.util.List;

public class RuleEngine {

    public static final int NUMBER_OF_HANDCARDS = 7;

    public static int calculateScore(List<Card> cards) {
        if (cards.size() != NUMBER_OF_HANDCARDS) {
            throw new IllegalArgumentException("Number of cards must be " + NUMBER_OF_HANDCARDS);
        }

        return cards.stream()
                .sorted((card1, card2) -> new CardSetComparator().compare(card1, card2))
                .mapToInt(Card::getValue)
                .sum();

    }

    /**
     * Calculates of the hand is winning and returns the score
     */
    public static int isWinning(List<Card> cards) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
